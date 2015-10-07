using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.IO;
using RestSharp;
using System.Runtime.Serialization;
using System.Runtime.Serialization;


namespace ConsoleApplication1
{

    class trip
    {
        public String loginResult { get; set; }
    }

    class Program
    {
        static void Main(string[] args)
        {
            var client = new RestClient("http://drivestatsrest.cloudapp.net//RestService.svc/");
            //login test
            var reqL = new RestRequest("login/{id}", Method.GET);
            reqL.AddUrlSegment("id", "Zander.boshoff@gmail.com");
            Console.WriteLine("contacting server");
            IRestResponse res = client.Execute(reqL);
            Console.WriteLine("Login Successful");
            Console.WriteLine(res.Content);

            var req = new RestRequest("addtrip/", Method.POST);
            req.RequestFormat = DataFormat.Json;
            

            StreamReader streamReader = new StreamReader("dataSample3.dat");
            string text = streamReader.ReadToEnd();
            streamReader.Close();
            req.AddJsonBody(new { userID = "1", tripDate = DateTime.Now.ToString("dd/MM/yyyy"), startTime = DateTime.Now.ToString("HH:mm"), data = text });
            
            res = client.Execute(req);
            Console.WriteLine("Add sample trip successful");
            Console.WriteLine(res.Content);

            Console.ReadLine();

        }
    }
}
