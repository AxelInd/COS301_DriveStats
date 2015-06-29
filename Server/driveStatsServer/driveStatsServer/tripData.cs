using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace driveStatsServer
{
    public class tripData
    {
        public virtual int ID { get; set; }

        public virtual string latitude { get; set; }
        public virtual string longitude { get; set; }
        public virtual double speed { get; set; }
        public virtual string recTime { get; set; }
        public virtual double maxXAcelerometer { get; set; }
        public virtual double maxYAcelerometer { get; set; }
        public virtual double maxZAcelerometer { get; set; }
    }
}