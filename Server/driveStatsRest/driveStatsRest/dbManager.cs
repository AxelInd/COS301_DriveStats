using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace driveStatsRest
{
    public class dbManager
    {
        public double getAverageScore()
        {
            double score = 0;
            using (var context = new drivestatsEntities())
            {
                score = (double)context.users.Select(u => u.averageScore).Average();
            }
            return score;
        }

        private void addUser(string email)
        {
            user newUser = new user();
            newUser.email = email;
            newUser.joinDate = DateTime.Now.ToShortDateString();
            newUser.averageScore = 0;
            using (var context = new drivestatsEntities())
            {
                context.users.Add(newUser);
                context.SaveChanges();
            }
        }
        public string getUserID(string email)
        {
            string id = "-1";
            using (var context = new drivestatsEntities())
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
        public int addUserTrip(trip ut)
        {
            int id = -1;
            using (var context = new drivestatsEntities())
            {
                context.trips.Add(ut);
                context.SaveChanges();
                id = ut.ID;
            }
            return id;
        }
        public trip getUserTrip(int id)
        {
                using (var context = new drivestatsEntities())
                {
                    var query = context.trips.Where(t => t.ID == id);
                    var result = query.First();
                    return result;
                }
        }

        public void inserTripData(tripData td)
        {
            using (var context = new drivestatsEntities())
            {
                context.tripDatas.Add(td);
                context.SaveChanges();
            }
        }

        public double getSpeedData()
        {
            return 0;
        }
    }
}