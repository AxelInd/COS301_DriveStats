using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace newDBClient
{
    public partial class Form1 : Form
    {
        List<int> lip = new List<int>();
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            using (var ctx = new drivestatsEntities())
            {
                List<trip> li = ctx.trips.ToList();
                for (int k = 0; k < li.Count; k++)
                {
                    lsbTrips.Items.Add(li[k].ID);
                }
            }
        }

        private void lsbTrips_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (lsbTrips.SelectedIndex == -1)
                return;

            using (var ctx = new drivestatsEntities())
            {
                int temp = int.Parse(lsbTrips.SelectedItem.ToString());

                var res = ctx.trips.Where(s => s.ID == temp).FirstOrDefault();
                List<tripData> li = ctx.tripDatas.Where(s => s.tripID == res.ID).ToList();
                lblUser.Text = res.user.email;
                lblDate.Text = res.tripDate;
                lblTime.Text = res.startTime;
                lblCount.Text = li.Count.ToString();
            }
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            lsbPicked.Items.Add(lsbTrips.SelectedItem);
            lip.Add(int.Parse(lsbTrips.SelectedItem.ToString()));

        }

        private void btnMake_Click(object sender, EventArgs e)
        {
            using (var ctx = new drivestatsEntities())
            {
                using (System.IO.StreamWriter file = new System.IO.StreamWriter("dbData.txt", true))
                {
                    for (int k = 0; k < lsbPicked.Items.Count; k++)
                    {
                        int temp = lip[k];
                        List<tripData> li = ctx.tripDatas.Where(s => s.tripID == temp).ToList();
                        for (int j = 0; j < li.Count; j++)
                        {

                            //[-25.759146095118535, 28.237862946508436];1.0;-1.927;7.305;10.072;
                            string coords = "[" + li[j].latitude + ", " + li[j].longitude + "];";
                            string dotData = li[j].speed + ";" + li[j].maxXAcelerometer + ";" + li[j].maxYAcelerometer + ";" + li[j].maxZAcelerometer + ";";
                            //dotData.Replace(',', '.');
                            file.WriteLine(coords + dotData);
                        }
                    }
                }
            }
            MessageBox.Show("done");
        }

        private void changeAlgValuesToolStripMenuItem_Click(object sender, EventArgs e)
        {
            algorithm temp = new algorithm();
            temp.ShowDialog();
        }
    }
}
