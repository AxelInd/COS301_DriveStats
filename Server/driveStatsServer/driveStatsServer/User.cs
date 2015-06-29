using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace driveStatsServer
{
    public class User
    {
        public virtual int ID { get; set; }

        public virtual string email { get; set; }
        public virtual string joinDate { get; set; }
        public virtual double averageScore { get; set; }
    }
}