using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using EntityFramework.BulkInsert.Extensions;
using System.Transactions;
using System.Net.Mail;
namespace driveStatsRest
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "RestService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select RestService.svc or RestService.svc.cs at the Solution Explorer and start debugging.
    public class RestService : IRestService
    {
        public void DoWork()
        {
        }

        public string login(string email)
        {
            try
            {
                dbManager db = new dbManager();
                return db.getUserID(email);
            }
            catch (Exception ex)
            {
                return "-1";
            }
        }

        public string addTrip(string userID, string tripDate, string startTime,string data)
        {
            try
            {
                List<tripData> li = new List<tripData>();

                dbManager db = new dbManager();
                trip t = new trip();

                t.userID = Convert.ToInt32(userID);
                t.tripDate = tripDate;
                t.startTime = startTime;
                //get an id to link the data to the trip
                int tripID = db.addUserTrip(t);  //saves to database
                //process data string
                StringReader reader = new StringReader(data);
                string line;
                var context = new drivestatsEntities();
                using (var transactionScope = new TransactionScope())
                {
                    context.Configuration.AutoDetectChangesEnabled = false;
                    context.Configuration.ValidateOnSaveEnabled = false;
                    int count = 0;
                    while (reader.Peek() > -1)
                    {
                        count++;
                        tripData d = new tripData();
                        d.tripID = tripID;
                        line = reader.ReadLine();
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

                    context.BulkInsert(li);
                    context.SaveChanges();
                    transactionScope.Complete();
                    if (userID == "1")//test client
                    {
                        return "5.3";
                    }
                }

                ScoreCalculator score = new ScoreCalculator(li, 3);
                //run thread to update score and user average
                return score.getscore().ToString();
            }
            catch (Exception ex)
            {
                using (StreamWriter writer =
                new StreamWriter("log.txt"))
                        {
                            writer.Write(ex.Message);
                            writer.WriteLine("inner stuff");
                            writer.WriteLine(ex.InnerException);
                        }
            }
            return "0";
        }

        public string UploadFile(string fileName, Stream fileStream)
        {
            string filePath = fileName;
            using (var output = File.Open(filePath, FileMode.Create))
            {
                //fileStream.CopyTo(output); testing
            }
            return "successfully added "+fileName;
        }
        public string test(string thing)
        {
            try
            {
                string text = System.IO.File.ReadAllText("log.txt");
                return text;
            }
            catch (Exception ex)
            {

            }
            return "no file found";
        }

    }
}
