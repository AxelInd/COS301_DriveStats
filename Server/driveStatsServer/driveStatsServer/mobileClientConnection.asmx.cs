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
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // 
    public class mobileClientConnection : System.Web.Services.WebService
    {

        [WebMethod]
        public string HelloWorld()
        {
            return "Hello World";
        }
        [WebMethod]
        public Boolean login(string email)
        {
            dbManager temp = new dbManager();

            User u = new User();
            u.email = "new@test.temp";
            u.joinDate = DateTime.Now.ToShortDateString();
            temp.addUser(u);
            return true;
        }
    }
}
