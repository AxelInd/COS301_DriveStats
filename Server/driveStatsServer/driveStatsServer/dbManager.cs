using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate;
using NHibernate.Cfg;

namespace driveStatsServer
{
    public class dbManager
    {
        private Configuration myConfig;
        private ISessionFactory mySessionfactory;
        private ISession mySession;

        public dbManager()
        {
            myConfig = new Configuration();
            myConfig.Configure();
            mySessionfactory = myConfig.BuildSessionFactory();
            mySession = mySessionfactory.OpenSession();
        }

        
        /**
         * @return average score of all users
         **/
        public double getAverageScore() 
        {
            //to do 
            return 5;
        }
        public void addUser(User u)
        {
            try
            {
                using (mySession.BeginTransaction())
                {
                    mySession.Save(u);
                    mySession.Transaction.Commit();
                }

            }
            finally
            {
                mySession.Close();
            }
        }
    }
}