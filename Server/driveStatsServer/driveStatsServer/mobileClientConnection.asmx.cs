using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace driveStatsServer
{
    /// <summary>
    /// Summary description for mobileClientConnection
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    [System.Web.Script.Services.ScriptService]
    
    public class mobileClientConnection : System.Web.Services.WebService
    {

        [WebMethod]
        public string HelloWorld()
        {
            return "Hello World";
        }

        [WebMethod]
        public string login(string email)
        {
            dbManager db = new dbManager();
            return db.getUserID(email);
        }

        [WebMethod]
        public string scoreTest()
        {
            dbManager db = new dbManager();
            return db.getAverageScore().ToString();
        }

        
    }
}
