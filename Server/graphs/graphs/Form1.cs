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
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void averageScoreToolStripMenuItem_Click(object sender, EventArgs e)
        {
            avgScore temp = new avgScore();
            temp.ShowDialog();
        }

        private void tripDetailsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            tripDetails temp = new tripDetails();
            temp.ShowDialog();
        }
    }
}
