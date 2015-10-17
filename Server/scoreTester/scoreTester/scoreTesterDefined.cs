using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace scoreTester
{
    class scoreTesterDefined : ScoreCalculator
    {
        public scoreTesterDefined()
        {  
            testAll();
        }
        public void testAll()
        {
            debugMessageBox("UNIT TESTING....");
            trips = createMockData();
            
            //=============================================================================
            //=============================TESTING CONVERSIONS=============================
            try
            {
                replaceStopsWithCommas();
                
            }
            catch (Exception e)
            {
                debugMessageBox(">> Replacing fullstops with commas failed\n" + e.StackTrace);
            }
            //=============================================================================
            //===========================TESTING DATA EXTRACTION===========================
            try
            {
                List<double>lix = getAllX();
                List<double> liy = getAllY();
                List<double> liz = getAllZ();

                if (lix.Count != liy.Count || lix.Count != liz.Count || liy.Count != liz.Count || lix.Count != trips.Count)
                {
                    debugMessageBox("Length of x, y, z lists do not match");
                }


            }
            catch (Exception e)
            {
                debugMessageBox(">> Data extraction failed\n" + e.StackTrace);
            }
            //=============================================================================
            //================================TESTING MEANS================================
            try
            {
                //NB: Don't forget that the mean is @TODO to be calculated with absolute values
                double avX = getMeanX();
                double avY = getMeanY();
                double avZ = Math.Round(getMeanZ(),5);

                double expectedAvX = -3.8814;
                double expectedAvY = -1.2117;
                double expectedAvZ = -0.78;


                string means = "--Observed:: \n" + "Mean x : " + avX + "\n" + "Mean Y : " + avY + "\n" + "Mean Z : " + avZ + "\n";
                means += "--Expected:: \n" + "Mean x : " + expectedAvX + "\n" + "Mean Y : " + expectedAvY + "\n" + "Mean Z : " + expectedAvZ + "\n";

                if (avX != expectedAvX || avY != expectedAvY || avZ != expectedAvZ)
                {
                    debugMessageBox(">> Logical failure in calculating averages\n" + means);
                }
            }
            catch (Exception e)
            {
                debugMessageBox(">> Testing means failed\n" + e.StackTrace);
            }
            //=============================================================================
            //===============================TESTING STDDEV================================
            try
            {
                double stdX = Math.Round(getStandardDeviationX(),5);
                double stdY = Math.Round(getStandardDeviationY(),5);
                double stdZ = Math.Round(getStandardDeviationZ(),5);

                double expectedAvX = Math.Round(0.009941831,5);
                double expectedAvY = Math.Round(0.015969033, 5);
                double expectedAvZ = Math.Round(0.018574176, 5);

                string stds = "--Observed:: \n" + "Standard Dev x : " + stdX + "\n" + "Standard Dev Y : " + stdY + "\n" + "Standard Dev Z : " + stdZ + "\n";
                stds += "--Expected:: \n" + "Standard Dev x : " + expectedAvX + "\n" + "Standard Dev Y : " + expectedAvY + "\n" + "Standard Dev Z : " + expectedAvZ + "\n";
                if (stdX != expectedAvX || stdY != expectedAvY || stdZ != expectedAvZ)
                {
                    debugMessageBox(">> Logical failure in standard deviation\n" + stds);
                }
            }
            catch (Exception e)
            {
                debugMessageBox(">> Getting standard deviations failed\n" + e.StackTrace);
            }

            //=============================================================================
            //===============================TESTING BTPS==================================
            try
            {
                double observedTotalofBadThingsPerSecond = Math.Round(weightedTotalofBadThingsPerSecond(), 5);
                double expectedTotalOfBadThingsPerSecond = 1.13333;
                string to = "Expected number of bad things : " + expectedTotalOfBadThingsPerSecond + "\nObserved total of bad things per second : " + observedTotalofBadThingsPerSecond;
                if (observedTotalofBadThingsPerSecond != expectedTotalOfBadThingsPerSecond)
                {
                    debugMessageBox(">> Logical failure in errors per second\n" + to);
                }
            }
            catch (Exception e)
            {
                debugMessageBox(">>Bad things per second failed\n" + e.StackTrace);
            }



        }



        private List<tripData> createMockData()
        {
            List<tripData> t = new List<tripData>();
            double[] xVals = { -3.892, -3.878, -3.896, -3.881, -3.897, -3.874, -3.874, -3.881, -3.876, -3.865 };
            double[] yVals = { -1.199, -1.213, -1.214, -1.188, -1.194, -1.211, -1.238, -1.24, -1.212, -1.208 };
            double[] zVals = { 9.016, 9.014, 9.032, 8.993, 9.018, 9.042, 9.06, 9.01, 9.011, 9.004 };
            double[] speed = { 10,10,10,10,10,10,10,10,10,10};
            
            double[] xCoordinates = { -25.0, -25.0, -25.0, -25.0, -25.0, -25.0, -25.0, -25.0, -25.0, -25.0 };
            double[] yCoordinates = { -25.0, -25.0, -25.0, -25.0, -25.0, -25.0, -25.0, -25.0, -25.0, -25.0 };

           // debugMessageBox("xVals[0] is " + xVals[0]);
            for (int i = 0; i < 10; i++)
            {
                t.Add(new tripData());
                t[i].maxXAcelerometer = ""+xVals[i];
                t[i].maxYAcelerometer = "" + yVals[i];
                t[i].maxZAcelerometer = "" + zVals[i];
                t[i].latitude = "" + xCoordinates[i];
                t[i].longitude = "" + yCoordinates[i];
                t[i].speed = "" + speed[i];
            }

            //debugMessageBox("X after insert is " + t[0].maxXAcelerometer);


            return t;

        }
    }
}
