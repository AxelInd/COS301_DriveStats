using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace driveStatsServer
{
    public class dbManager
    {
        public double getAverageScore()
        {
            double score = 0;
            using (var context = new driveStatsEntities())
            {
                score = (double) context.users.Select(u => u.averageScore).Average();
            }
            return score;
        }

        public string getUserID (string email)
        {
            string id = "-1";
            using (var context = new driveStatsEntities())
            {
                var query = context.users.Where(s => s.email == email);
                var dbuser = query.FirstOrDefault<user>();
                if (dbuser == null)
                {
                    addUser(email);
                    var queryNew = context.users.Where(s => s.email == email);
                    var dbuserNew = query.FirstOrDefault<user>();
                    id = dbuserNew.ID.ToString();
                }
                else
                {
                    id = dbuser.ID.ToString();
                }
            }
            return id;
        }

        private void addUser(string email)
        {
            user newUser = new user();
            newUser.email = email;
            newUser.joinDate = DateTime.Now.ToShortDateString();
            newUser.averageScore = 0;
            using (var context = new driveStatsEntities())
            {
                context.users.Add(newUser);
                context.SaveChanges();
            }
        }

        public void addUserTrip(trip ut)
        {
            using (var context = new driveStatsEntities())
            {
                context.trips.Add(ut);
                context.SaveChanges();
            }
        }

        public trip getUserTrip(int id)
        {
            try
            {
                using (var context = new driveStatsEntities())
                {
                    var query = context.trips.Where(t => t.ID == id);
                    var result = query.First();
                    return result;
                }
            }
            catch (Exception ex)
            {
                return null;
            }
            
        }

        public void inserTripData(tripData td)
        {
            using (var context = new driveStatsEntities())
            {
                context.tripDatas.Add(td);
                context.SaveChanges();
            }
        }

        public factor getFactors()
        {
            using (var context = new driveStatsEntities())
            {
                var query = context.factors.First();
                return query;
            }
        }

        public double getSpeedData()
        {
            return 0;
        }

    }
}