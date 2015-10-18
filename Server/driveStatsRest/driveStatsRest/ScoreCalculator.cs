using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace driveStatsRest
{
    /**
 * ALGORITHM DESCRIPTION:
 * (1) Decide on the weighted number of bad things a user does ever second
 * (2) We compare the weighted number of bad things per second to the average number of bad things per second for the database
 * as a whole using a normal distribution to get the number of std deviations from the mean
 * (3) using this and the known number of bad things per second we can find a percentage chance that the data occured by random chance.
 * (4) This percentage chance is then adjusted to give a score following the normal distribution with stdDev = 15%
 **/
    class ScoreCalculator
    {
        protected bool debugging = false;
        protected List<tripData> trips;
        public ScoreCalculator()
        {
        }
        public ScoreCalculator(List<tripData> trips, double interval)
        {

            //MessageBox.Show(trips.Count.ToString());
            this.trips = trips;
            replaceStopsWithCommas();




            string means = "Means\n" + "Mean x : " + getMeanX() + "\n" + "Mean Y : " + getMeanY() + "\n" + "Mean Z : " + getMeanZ();
            debugMessageBox(means);
            //first we need mean and standard deviation for each variable
            List<double> allx = getAllX();
            List<double> ally = getAllY();
            List<double> allz = getAllZ();



            string listSize = "Size of list x is " + allx.Count + "\n";
            listSize += "Size of list y is " + ally.Count + "\n";
            listSize += "Size of list z is " + allz.Count + "\n";



            string stddev = "stddev of list x is " + getStandardDeviationX() + "\n";
            stddev += "stddev of list y is " + getStandardDeviationY() + "\n";
            stddev += "stddev of list z is " + getStandardDeviationZ() + "\n";


            debugMessageBox("Standard deviations are " + stddev);
            debugMessageBox("List size is " + listSize);


        }


        /** 
 * Returns the height of the normal distribution at the specified z-score
 */
        protected double getNormalProbabilityAtZ(double z)
        {
            return Math.Exp(-Math.Pow(z, 2) / 2) / Math.Sqrt(2 * Math.PI);
        }

        /**
  * Returns the area under the normal curve between the z-scores z1 and z2
  */
        protected double getAreaUnderNormalCurve(double z1, double z2)
        {
            double area = 0.0;
            int rectangles = 100000; // more rectangles = more precise, less rectangles = quicker execution
            double width = (z2 - z1) / rectangles;
            for (int i = 0; i < rectangles; i++)
                area += width * getNormalProbabilityAtZ(width * i + z1);
            return area;
        }






        protected void debugMessageBox(string message)
        {
            if (debugging == true)
            {
                //MessageBox.Show(message);
            }
        }


        public double getscore()
        {
            // We except normal distribution of weighted elements with mean and total both equal to the weighted sum.

            //average bad things of this person
            double badThingsPerSecond = weightedTotalofBadThingsPerSecond();
            debugMessageBox("The number of bad things per second " + badThingsPerSecond.ToString());
            //average bad things of database as a whole
            // @TODO
            double TRUEAVERAGENUMBEROFBADTHINGSPERSECOND = 0.4;
            double STANDARDDEVIATIONOFPOPULATION = 0.4;
            //getStandardDeviationScorePerSecond() this population needs to be called locally.

            double prob = normalDistribution(badThingsPerSecond, TRUEAVERAGENUMBEROFBADTHINGSPERSECOND, STANDARDDEVIATIONOFPOPULATION);
            debugMessageBox("probability is " + prob);
            debugMessageBox("Standard deviation of scores per second is " + getStandardDeviationScorePerSecond());
            debugMessageBox("zScore is " + getZScore(TRUEAVERAGENUMBEROFBADTHINGSPERSECOND, STANDARDDEVIATIONOFPOPULATION, badThingsPerSecond));

            double zScore = getZScore(TRUEAVERAGENUMBEROFBADTHINGSPERSECOND, STANDARDDEVIATIONOFPOPULATION, badThingsPerSecond);
            double area = 0.5 + getAreaUnderNormalCurve(0, zScore);

            return area * 10;
        }

        protected double getZScore(double mu, double standardDeviation, double badThingsPerSecond)
        {

            double zScore = (badThingsPerSecond - mu) / standardDeviation;
            return zScore;
        }

        //@TODO add z-score tables

        //=======================================================================================================================


        protected double normalDistribution(double personAv, double trueAv, double standardDeviationOfPopulation)
        {
            double normalDist1 = 1.0 / (standardDeviationOfPopulation * Math.Sqrt(2 * Math.PI));
            double normalDist2 = Math.Pow(Math.E, Math.Pow(personAv - trueAv, 2) / (-2 * standardDeviationOfPopulation * standardDeviationOfPopulation));
            double normalDist = normalDist1 * normalDist2;

            return normalDist;
        }

        protected double weightedTotalofBadThingsPerSecond()
        {
            if (timeOfTripInSeconds() == 0)
            {
                throw new DivideByZeroException();
            }
            return weightedTotalOfBadThings() / timeOfTripInSeconds();
        }

        protected double timeOfTripInSeconds()
        {
            return trips.Count / 3;
        }

        protected double weightedTotalOfBadThings()
        {
            double weightX = 1;
            double weightY = 0.6;
            double weightZ = 0.4;
            double badSpeedWeight = 7;

            double totalWeightOfBadThings = 0;

            List<double> allX = getAllX();
            List<double> allY = getAllY();
            List<double> allZ = getAllZ();
            List<double> allSpeed = getAllZ();
            for (int i = 0; i < trips.Count; i++)
            {
                totalWeightOfBadThings += weightX * xExceeded(allX[i]) + weightY * yExceeded(allY[i]) + weightZ * zExceeded(allZ[i]) + badSpeedWeight * speedExceeded(allSpeed[i]);


            }

            debugMessageBox("Weighted total of bad things is " + totalWeightOfBadThings);



            return totalWeightOfBadThings;
        }

        protected double getWeightedTotalOfOneData(int pos)
        {
            double weightX = 1;
            double weightY = 0.6;
            double weightZ = 0.4;
            double badSpeedWeight = 7;

            double totalWeightOfBadThings = weightX * xExceeded(getAllX()[pos]) + weightY * yExceeded(getAllY()[pos]) + weightZ * zExceeded(getAllZ()[pos]) + badSpeedWeight * speedExceeded(getAllSpeed()[pos]);
            return totalWeightOfBadThings;
        }

        double MAXX = 4.2;
        double MAXY = 3;
        double MAXZ = 2;
        double MAXSPEED = 36;
        protected double speedExceeded(double speed)
        {
            return checkExceeded(Math.Abs(speed), MAXSPEED);
        }
        protected double xExceeded(double x)
        {
            return checkExceeded(Math.Abs(x), MAXX);
        }
        protected double yExceeded(double y)
        {
            return checkExceeded(Math.Abs(y), MAXY);
        }
        protected double zExceeded(double z)
        {
            return checkExceeded(Math.Abs(z), MAXZ);
        }

        protected double checkExceeded(double value, double threshold)
        {
            if (value > 2 * threshold)
            {
                return 2;
            }
            if (value > threshold)
            {
                return 1;
            }
            return 0;



        }




        protected List<double> getAllX()
        {
            List<double> allX = new List<double>();
            for (int i = 0; i < trips.Count; i++)
            {

                //allX.Add(Math.Abs(Convert.ToDouble(trips[i].maxXAcelerometer)));
                allX.Add(Convert.ToDouble(trips[i].maxXAcelerometer));
            }
            return allX;
        }

        protected List<double> getAllY()
        {
            List<double> allY = new List<double>();
            for (int i = 0; i < trips.Count; i++)
            {

                //allY.Add(Math.Abs(Convert.ToDouble(trips[i].maxYAcelerometer)));
                allY.Add(Convert.ToDouble(trips[i].maxYAcelerometer));
            }
            return allY;
        }
        protected List<double> getAllZ()
        {
            List<double> allZ = new List<double>();
            for (int i = 0; i < trips.Count; i++)
            {
                //9.8 refers to gravity
                //allZ.Add(Math.Abs(Convert.ToDouble(trips[i].maxZAcelerometer)) - 9.8);
                allZ.Add(Convert.ToDouble(trips[i].maxZAcelerometer) - 9.8);
            }
            return allZ;
        }
        protected List<double> getAllSpeed()
        {
            List<double> allspeed = new List<double>();
            for (int i = 0; i < trips.Count; i++)
            {
                trips[i].speed = trips[i].speed.Replace('.', ',');
                allspeed.Add(Math.Abs(Convert.ToDouble(trips[i].speed)) - 9.8);
            }
            return allspeed;
        }

        protected void replaceStopsWithCommas()
        {
            for (int i = 0; i < trips.Count; i++)
            {
                trips[i].maxXAcelerometer = trips[i].maxXAcelerometer.Replace('.', ',');
                trips[i].maxZAcelerometer = trips[i].maxZAcelerometer.Replace('.', ',');
                trips[i].maxYAcelerometer = trips[i].maxYAcelerometer.Replace('.', ',');
            }
        }

        protected List<double> getAllScorePerSecond()
        {
            List<double> allspeed = new List<double>();
            for (int i = 0; i < trips.Count; i++)
            {

                allspeed.Add(getWeightedTotalOfOneData(i));
            }
            return allspeed;
        }



        /**
 * Mean of the X acceleration
 **/
        protected double getMeanSpeed()
        {
            return average(getAllSpeed());
        }

        /**
         * Mean of the X acceleration
         **/
        protected double getMeanX()
        {

            return average(getAllX());
        }

        /**
 * Mean of the Y acceleration
 **/
        protected double getMeanY()
        {
            return average(getAllY());
        }


        /**
* Mean of the Z acceleration
**/
        protected double getMeanZ()
        {
            return average(getAllZ());
        }
        protected double average(List<double> li)
        {
            double total = 0;

            for (int i = 0; i < li.Count; i++)
            {
                total += li[i];
            }
            return total / li.Count;
        }


        protected double getStandardDeviation(List<double> l)
        {
            double avg = average(l);
            return Math.Sqrt(l.Average(v => Math.Pow(v - avg, 2)));

        }

        protected double getStandardDeviationX()
        {
            return getStandardDeviation(getAllX());
        }
        protected double getStandardDeviationY()
        {
            return getStandardDeviation(getAllY());
        }
        protected double getStandardDeviationZ()
        {
            return getStandardDeviation(getAllZ());
        }
        protected double getStandardDeviationScorePerSecond()
        {
            return getStandardDeviation(getAllScorePerSecond());
        }

    }

}