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
    public partial class algorithm : Form
    {
        public algorithm()
        {
            InitializeComponent();
        }

        private void algorithmDataBindingNavigatorSaveItem_Click(object sender, EventArgs e)
        {
            this.Validate();
            this.algorithmDataBindingSource.EndEdit();
            this.tableAdapterManager.UpdateAll(this.drivestatsDataSet);

        }

        private void algorithm_Load(object sender, EventArgs e)
        {
            // TODO: This line of code loads data into the 'drivestatsDataSet.algorithmData' table. You can move, or remove it, as needed.
            this.algorithmDataTableAdapter.Fill(this.drivestatsDataSet.algorithmData);

        }
    }
}
