using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace graphs
{
    public partial class tripDetails : Form
    {
        public tripDetails()
        {
            InitializeComponent();
        }

        private void tripDetails_Load(object sender, EventArgs e)
        {
            using (var ctx = new drivestatsdbEntities())
            {
                var res = ctx.users.ToList();
                for (int k = 0; k < res.Count; k++)
                {
                    cbxUsers.Items.Add(res[k].email);
                }
            }
        }

        private void cbxUsers_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (cbxUsers.SelectedIndex == -1)
            {
                return;
            }
            using (var ctx = new drivestatsdbEntities())
            {
                var res = ctx.users.Where(s => s.email == cbxUsers.SelectedItem.ToString()).FirstOrDefault();
                List<trip> t = res.trips.ToList();
                for (int k = 0; k < t.Count; k++)
                    cbxTrips.Items.Add(t[k].ID);
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (cbxTrips.SelectedIndex == -1)
            {
                return;
            }
            using (var ctx = new drivestatsdbEntities())
            {
                chart1.Series["x"].Points.Clear();
                chart1.Series["y"].Points.Clear();
                chart1.Series["z"].Points.Clear();
                var res = ctx.users.Where(s => s.email == cbxUsers.SelectedItem.ToString()).FirstOrDefault();
                trip t = res.trips.Where(s => s.ID==Convert.ToInt32(cbxTrips.SelectedItem)).FirstOrDefault();
                List<tripData> data = ctx.tripDatas.Where(s => s.tripID == t.ID).ToList();
                for (int k = 0; k < data.Count; k++)
                {
                    chart1.Series["x"].Points.AddY(data[k].maxXAcelerometer);
                    chart1.Series["y"].Points.AddY(data[k].maxYAcelerometer);
                    chart1.Series["z"].Points.AddY(data[k].maxZAcelerometer);
                }
                
            }
        }
    }
}
