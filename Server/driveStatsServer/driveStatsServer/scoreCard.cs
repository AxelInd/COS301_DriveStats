using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace driveStatsServer
{
    public class scoreCard
    {
        dbManager db;
        userTrip trip {get; set;}
        public scoreCard(dbManager db)
        {
            this.db = db;
        }
        public double calculateAbsoluteScore()
        {
            //to do
            return 5;
        }
        private double calculateReativeScore(double num, double overAllAverageScoreOfuserScore)
        {
            //to do
            return 5;
        }
        public double calculateRelativeScore()
        {
            return calculateReativeScore(calculateAbsoluteScore(), db.getAverageScore());
        }

    }
}