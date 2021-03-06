﻿using System;
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
            trips = createMockData();
            testAll();
        }
        public scoreTesterDefined(string fileName)
        {
            runFromFile(fileName);
            testAll();
        }


        public bool testGenericFunctions()
        {
            bool conversion = testConverstions();
            bool extraction = testDataExtractions();

            return !(conversion || extraction);
        }
        public bool basicCalculationsOnData()
        {
            bool means = testMeans();
            bool stdDev = testStdDevs();
            bool BTPS = testBTPS();

            return !(means || stdDev || BTPS);
        }
        public bool testStatsFunctions()
        {
            //@TODO must be improved
            bool normalDis = testNormalDistribution();
            bool zScore = testZScores();
            bool areaUnderCurve = testAreaUnderCurve();
            return !(normalDis || zScore || areaUnderCurve);
        }

        public bool testAll()
        {
            debugMessageBox("UNIT TESTING....");
            
            init();


            bool genericFunctions = testGenericFunctions();
            bool basicCalculculations = basicCalculationsOnData();
            bool statsFunctions = testStatsFunctions();


            List<double> allbadThings = getAllScorePerSecond();
            for (int i = 0; i < 1000; i++)
            {
                debugMessageBox("" + allbadThings[i]);
            }




            return !(genericFunctions || basicCalculculations || statsFunctions);
        }

        /**
         * WORKS
        **/ 
        private bool testZScores()
        {
            //=============================================================================
            //============================CALCULATE Z-SCORE================================
            try
            {
                double badThingsPerSecond = 5;
                double TRUEAVERAGENUMBEROFBADTHINGSPERSECOND = 4;
                double STANDARDDEVIATIONOFPOPULATION = 4;
                double observedZScore = getZScore(badThingsPerSecond, TRUEAVERAGENUMBEROFBADTHINGSPERSECOND, STANDARDDEVIATIONOFPOPULATION);
                double expectedZScore = -0.25;

                string to = "Expected z score : " + expectedZScore + "\nObserved z score : " + observedZScore;
                if (observedZScore != expectedZScore)
                {
                    debugMessageBox(">> Z Score \n" + to);
                    return false;
                }
            }
            catch (Exception e)
            {
                debugMessageBox(">>Z-Score fail\n" + e.StackTrace);
                return false;
            }
            return true;
        }
        /**
        * WORKS
        **/ 
        private bool testNormalDistribution()
        {


            //=============================================================================
            //============================TESTING NORMAL DIST==============================
            try
            {
                double personAv=3; //x
                double trueAv=5; //mu
                double standardDeviationOfPopulation = 5; //delta


                double observedProbabilityValue  = Math.Round(normalDistribution(personAv, trueAv, standardDeviationOfPopulation), 5);
                double expectedProbabilityValue  = 0.07365;
                string to = "Expected probability value : " + expectedProbabilityValue + "\nObserved probability value : " + observedProbabilityValue;
                if (observedProbabilityValue != expectedProbabilityValue)
                {
                    debugMessageBox(">> Normal distribution \n" + to);
                    return false;
                }
            }
            catch (Exception e)
            {
                debugMessageBox(">>Bad things per second failed\n" + e.StackTrace);
                return false;
            }
            return true;
        }


        private bool testAreaUnderCurve()
        {

            //=============================================================================
            //========================CALCULATE AREA UNDER CURVE===========================


            try
            {
                double ZScore = 1.2;

                double observedNormalPob = Math.Round(getAreaUnderNormalCurve(0, ZScore),5);
                double expectedNormalProb = 0.38493;

                string to = "Area under curve\nObserved area = " + observedNormalPob + "\nExpected normal prob = " + expectedNormalProb;
                if (observedNormalPob != expectedNormalProb)
                {
                    debugMessageBox(">> Z Score \n" + to);
                    return false;
                }
            }
            catch (Exception e)
            {
                debugMessageBox(">>Area fail\n" + e.StackTrace);
                return false;
            }
            return true;
        }

        private bool testStdDevs()
        {
            //=============================================================================
            //===============================TESTING STDDEV================================
            try
            {
                List<double> values = new List<double>();
                values.Add(600);
                values.Add(470);
                values.Add(170);
                values.Add(430);
                values.Add(300);


                double expectedDeviation = 147;
                double observedDeviation = Math.Round(getStandardDeviation(values));


                string stds = "--Observed:: \n" + "Standard Dev : " + observedDeviation + "\n" + "Standard Dev Y : " + expectedDeviation;



                if (expectedDeviation != observedDeviation)
                {
                    debugMessageBox(">> Logical failure in standard deviation\n" + stds);
                    return false;
                }


                /**
                double stdX = Math.Round(getStandardDeviationX(), 5);
                double stdY = Math.Round(getStandardDeviationY(), 5);
                double stdZ = Math.Round(getStandardDeviationZ(), 5);

                
                double expectedAvY = Math.Round(0.015969033, 5);
                double expectedAvZ = Math.Round(0.018574176, 5);


                 **/


            }
            catch (Exception e)
            {
                debugMessageBox(">> Getting standard deviations failed\n" + e.StackTrace);
                return false;
            }
            return true;
        }
        private bool testMeans()
        {
            //=============================================================================
            //================================TESTING MEANS================================
            try
            {
                //NB: Don't forget that the mean is @TODO to be calculated with absolute values
                double avX = getMeanX();
                double avY = getMeanY();
                double avZ = Math.Round(getMeanZ(), 5);

                double expectedAvX = -3.8814;
                double expectedAvY = -1.2117;
                double expectedAvZ = -0.78;


                string means = "--Observed:: \n" + "Mean x : " + avX + "\n" + "Mean Y : " + avY + "\n" + "Mean Z : " + avZ + "\n";
                means += "--Expected:: \n" + "Mean x : " + expectedAvX + "\n" + "Mean Y : " + expectedAvY + "\n" + "Mean Z : " + expectedAvZ + "\n";

                if (avX != expectedAvX || avY != expectedAvY || avZ != expectedAvZ)
                {
                    debugMessageBox(">> Logical failure in calculating averages\n" + means);
                    return false;
                }
            }
            catch (Exception e)
            {
                debugMessageBox(">> Testing means failed\n" + e.StackTrace);
                return false;
            }
            return true;
        }
        private bool testBTPS()
        {


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
                    return false;
                }
            }
            catch (Exception e)
            {
                debugMessageBox(">>Bad things per second failed\n" + e.StackTrace);
                return false;
            }
            return false;
        }

        /**
        * WORKS
        **/ 
        private bool testDataExtractions()
        {
            //=============================================================================
            //===========================TESTING DATA EXTRACTION===========================
            try
            {
                List<double> lix = getAllX();
                List<double> liy = getAllY();
                List<double> liz = getAllZ();

                if (lix.Count != liy.Count || lix.Count != liz.Count || liy.Count != liz.Count || lix.Count != trips.Count)
                {
                    debugMessageBox("Length of x, y, z lists do not match");
                    return false;
                }


            }
            catch (Exception e)
            {
                debugMessageBox(">> Data extraction failed\n" + e.StackTrace);
                return false;
            }
            return true;
        }

        /**
        * WORKS
        **/ 
        private bool testConverstions()
        {
            

            //=============================================================================
            //=============================TESTING CONVERSIONS=============================
            try
            {
                replaceStopsWithCommas();

            }
            catch (Exception e)
            {
                debugMessageBox(">> Replacing fullstops with commas failed\n" + e.StackTrace);
                return false;
            }
            return true;
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
        private void runFromFile(string filename)
        {
            int counter = 0;
            string line;
            System.IO.StreamReader file = new System.IO.StreamReader(filename);
            List<tripData> li = new List<tripData>();
            while ((line = file.ReadLine()) != null)
            {
                tripData d = new tripData();
                counter++;
                string lat = line.Substring(1, line.IndexOf(',') - 1);
                line = line.Remove(0, lat.Length + 3);
                string lon = line.Substring(0, line.IndexOf(']'));
                line = line.Remove(0, lon.Length + 2);
                string speed = line.Substring(0, line.IndexOf(';'));
                line = line.Remove(0, speed.Length + 1);
                string x = line.Substring(0, line.IndexOf(';'));
                line = line.Remove(0, x.Length + 1);
                string y = line.Substring(0, line.IndexOf(';'));
                line = line.Remove(0, y.Length + 1);
                string z = line.Substring(0, line.IndexOf(';'));
                d.latitude = lat;
                d.longitude = lon;
                d.speed = speed;
                d.maxXAcelerometer = x;
                d.maxYAcelerometer = y;
                d.maxZAcelerometer = z;
                li.Add(d);
            }


            trips = li;
        }
    }
}
