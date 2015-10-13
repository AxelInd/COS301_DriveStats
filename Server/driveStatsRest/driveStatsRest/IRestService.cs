using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace driveStatsRest
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IRestService" in both code and config file together.
    [ServiceContract]
    public interface IRestService
    {
        [OperationContract]
        void DoWork();

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Json,
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "login/{email}")]
        string login(string email);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "UploadFile/{fileName}")]
        string UploadFile(string fileName, Stream fileStream);

        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Json,
            RequestFormat = WebMessageFormat.Json,
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "addtrip/")]
        string addTrip(string userID, string tripDate, string startTime,string data);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Json,
            
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "test/{thing}")]
        string test(string thing);
    }
}
