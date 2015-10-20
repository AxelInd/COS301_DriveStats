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
    public partial class avgScore : Form
    {
        public avgScore()
        {
            InitializeComponent();
        }

        private void avgScore_Load(object sender, EventArgs e)
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
                    if (t[k].score != null)
                        chart1.Series["score"].Points.AddY(t[k].score);
            }
        }

        private void chart1_Click(object sender, EventArgs e)
        {

        }
    }
}
