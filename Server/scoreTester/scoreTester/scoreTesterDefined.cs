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

            //=============================TESTING CONVERSIONS=============================
            try
            {
                replaceStopsWithCommas();
            }
            catch (Exception e)
            {
                debugMessageBox(">> Replacing fullstops with commas failed\n" + e.StackTrace);
            }

            //================================TESTING MEANS================================
            try
            {
                double avX = getMeanX();
                double avY = getMeanY();
                double avZ = getMeanZ();

                double expectedAvX = 3.8814;
                double expectedAvY = 9.02;
                double expectedAvZ = -8.5883;


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
            
            


        }



        private List<tripData> createMockData()
        {
            List<tripData> t = new List<tripData>();
            double[] xVals = { -3.892, -3.878, -3.896, -3.881, -3.897, -3.874, -3.874, -3.881, -3.876, -3.865 };
            double[] yVals = { 9.016, 9.014, 9.032, 8.993, 9.018, 9.042, 9.06, 9.01, 9.011, 9.004 };
            double[] zVals = { -1.199, -1.213, -1.214, -1.188, -1.194, -1.211, -1.238, -1.24, -1.212, -1.208 };
            double[] xCoordinates = { -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0 };
            double[] yCoordinates = { -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0, -25, 0 };

            for (int i = 0; i < 10; i++)
            {
                t.Add(new tripData());
                t[i].maxXAcelerometer = ""+xVals[i];
                t[i].maxYAcelerometer = "" + yVals[i];
                t[i].maxZAcelerometer = "" + zVals[i];
                t[i].latitude = "" + xCoordinates[i];
                t[i].longitude = "" + yCoordinates[i];
            }


            return t;

        }
    }
}
