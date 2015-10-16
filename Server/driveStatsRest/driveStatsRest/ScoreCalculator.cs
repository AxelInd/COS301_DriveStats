using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace driveStatsRest
{
    class ScoreCalculator
    {
        bool debugging = false;
        List<tripData> trips;

        public ScoreCalculator(List<tripData> trips, double interval)
        {
            //MessageBox.Show(trips.Count.ToString());
            this.trips = trips;

            debugMessageBox("Mean x " + getMeanX() + "");
            debugMessageBox("Mean Y " + getMeanY() + "");
            debugMessageBox("Mean Z " + getMeanZ() + "");
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
        private void debugMessageBox(string message)
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
            double score;
            double prob = normalDistribution(badThingsPerSecond, TRUEAVERAGENUMBEROFBADTHINGSPERSECOND);
            debugMessageBox("probability is " + prob);
            score = 5 + ((1 - prob) * 5);
            if (badThingsPerSecond < TRUEAVERAGENUMBEROFBADTHINGSPERSECOND)
            {
                prob = prob * -1;
                score = 5 + ((prob) * 5);
            }

            return Math.Round(score, 5);
        }
        private double normalDistribution(double personAv, double trueAv)
        {
            //double firstPart = (1 / Math.Sqrt(2 * Math.PI));
            //double secondPart = Math.Pow(Math.E, -(1 / 2) * ((personAv - trueAv) * (personAv - trueAv)) / Math.Sqrt(trueAv));
            //double secondPart = Math.Pow(Math.E, -(1 / 2) * (Math.Pow(personAv - trueAv,2)) / Math.Sqrt(trueAv));


            //double normalDist = 1/(Math.Sqrt(2*Math.PI))*Math.Pow(Math.E, -1/2 * Math.Pow(personAv-trueAv, 2)/Math.Sqrt(trueAv)) ;
            //double normalDist = 1.0 / (Math.Sqrt(2 * Math.PI)) * Math.Pow(Math.E, -1.0 / 2 * Math.Pow(personAv - trueAv, 2) / Math.Sqrt(trueAv));
            double normalDist1 = 1.0 / (Math.Sqrt(2 * Math.PI));
            double normalDist2 = Math.Pow(Math.E, Math.Pow(personAv - trueAv, 2) / (2 * Math.Sqrt(trueAv)));
            double normalDist = normalDist1 * normalDist2;

            //given a normal distribution, what is the chances of observing what is seen as percentage
            //double prob = firstPart * secondPart;
            //return prob;


            return normalDist;
        }

        private double weightedTotalofBadThingsPerSecond()
        {
            if (timeOfTripInSeconds() == 0)
            {
                throw new DivideByZeroException();
            }
            return weightedTotalOfBadThings() / timeOfTripInSeconds();
        }

        private double timeOfTripInSeconds()
        {
            return trips.Count / 3;
        }

        private double weightedTotalOfBadThings()
        {
            double weightX = 1;
            double weightY = 0.6;
            double weightZ = 0.4;
            double badSpeedWeight = 7;

            double totalWeightOfBadThings = weightX * countBadX() + weightY * countBadY() + weightZ * countBadZ() + badSpeedWeight * countBadSpeed();
            debugMessageBox("Weighted total of bad things is " + totalWeightOfBadThings);

            return totalWeightOfBadThings;
        }
        private double countBadSpeed()
        {
            double SPEEDTHRESHOLD = 35;
            List<double> li = getAllSpeed();
            double mean = li.Average();



            int numTimesT1Exceeded = 0;
            for (int i = 0; i < li.Count; i++)
            {
                if (li[i] > SPEEDTHRESHOLD)
                {
                    numTimesT1Exceeded++;
                }
            }

            double exceedingScore = numTimesT1Exceeded;

            return numTimesT1Exceeded;
        }

        private double countBadX()
        {
            return countBad(getAllX());
        }
        private double countBadY()
        {
            return countBad(getAllY());

        }
        private double countBadZ()
        {
            return countBad(getAllZ());
        }

        private double countBad(List<double> li)
        {
            double mean = li.Average();
            double threshold1 = mean + 1 * getStandardDeviation(li);
            double threshold2 = mean + 2 * getStandardDeviation(li);


            int numTimesT1Exceeded = 0;
            int numTimesT2Exceeded = 0;
            for (int i = 0; i < li.Count; i++)
            {
                if (li[i] >= threshold2)
                {
                    numTimesT2Exceeded++;
                }
                else if (li[i] >= threshold1)
                {
                    numTimesT1Exceeded++;
                }
            }

            double exceedingScore = numTimesT1Exceeded + 2 * numTimesT2Exceeded;

            return exceedingScore;

        }


        private List<double> getAllX()
        {
            List<double> allX = new List<double>();
            for (int i = 0; i < trips.Count; i++)
            {
                trips[i].maxXAcelerometer = trips[i].maxXAcelerometer.Replace('.', ',');
                allX.Add(Math.Abs(Convert.ToDouble(trips[i].maxXAcelerometer)));
            }
            return allX;
        }

        private List<double> getAllY()
        {
            List<double> allY = new List<double>();
            for (int i = 0; i < trips.Count; i++)
            {
                trips[i].maxYAcelerometer = trips[i].maxYAcelerometer.Replace('.', ',');
                allY.Add(Math.Abs(Convert.ToDouble(trips[i].maxYAcelerometer)));
            }
            return allY;
        }
        private List<double> getAllZ()
        {
            List<double> allZ = new List<double>();
            for (int i = 0; i < trips.Count; i++)
            {
                trips[i].maxZAcelerometer = trips[i].maxZAcelerometer.Replace('.', ',');
                allZ.Add(Math.Abs(Convert.ToDouble(trips[i].maxZAcelerometer)) - 9.8);
            }
            return allZ;
        }
        private List<double> getAllSpeed()
        {
            List<double> allspeed = new List<double>();
            for (int i = 0; i < trips.Count; i++)
            {
                trips[i].speed = trips[i].speed.Replace('.', ',');
                allspeed.Add(Math.Abs(Convert.ToDouble(trips[i].speed)) - 9.8);
            }
            return allspeed;
        }


        /**
 * Mean of the X acceleration
 **/
        private double getMeanSpeed()
        {
            return getAllSpeed().Average();
        }

        /**
         * Mean of the X acceleration
         **/
        private double getMeanX()
        {
            return getAllX().Average();
        }

        /**
 * Mean of the Y acceleration
 **/
        private double getMeanY()
        {
            return getAllY().Average();
        }


        /**
* Mean of the Z acceleration
**/
        private double getMeanZ()
        {
            return getAllZ().Average();
        }


        private double getStandardDeviation(List<double> l)
        {
            double avg = l.Average();
            return Math.Sqrt(l.Average(v => Math.Pow(v - avg, 2)));

        }

        private double getStandardDeviationX()
        {
            return getStandardDeviation(getAllX());
        }
        private double getStandardDeviationY()
        {
            return getStandardDeviation(getAllY());
        }
        private double getStandardDeviationZ()
        {
            return getStandardDeviation(getAllZ());
        }

    }

}