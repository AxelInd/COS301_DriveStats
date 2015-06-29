using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace driveStatsServer
{
    public class userTrip
    {
        public virtual int ID { get; set; }
        public virtual int userID { get; set; } //ref Users
        public virtual string startLatitude { get; set; }
        public virtual string stopLongitude { get; set; }
        public virtual List<tripData> tripReadings { get; set; }
    }
}