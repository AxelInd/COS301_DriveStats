using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace scoreTester
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            runFromFile("dataSample2.dat");
            //runWithTestData();
        }
        /**
         * UNIT TESTS
         **/ 
        private bool runWithTestData()
        {
            scoreTesterDefined st = new scoreTesterDefined();
            return false;
        }






        private void runFromFile(string filename)
        {
            int counter = 0;
            string line;
            System.IO.StreamReader file = new System.IO.StreamReader(filename);
            List<tripData> li = new List<tripData>();
            while ((line = file.ReadLine()) != null)
            {
                tripData d = new tripData();
                counter++;
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
            ScoreCalculator score = new ScoreCalculator(li, 3);
            MessageBox.Show("final score for trip is: " + score.getscore().ToString());
        }
    }
}
