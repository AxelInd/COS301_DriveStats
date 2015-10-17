namespace graphs
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.graphsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.averageScoreToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.tripDetailsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.graphsToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(652, 24);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // graphsToolStripMenuItem
            // 
            this.graphsToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.averageScoreToolStripMenuItem,
            this.tripDetailsToolStripMenuItem});
            this.graphsToolStripMenuItem.Name = "graphsToolStripMenuItem";
            this.graphsToolStripMenuItem.Size = new System.Drawing.Size(55, 20);
            this.graphsToolStripMenuItem.Text = "graphs";
            // 
            // averageScoreToolStripMenuItem
            // 
            this.averageScoreToolStripMenuItem.Name = "averageScoreToolStripMenuItem";
            this.averageScoreToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.averageScoreToolStripMenuItem.Text = "Average score";
            this.averageScoreToolStripMenuItem.Click += new System.EventHandler(this.averageScoreToolStripMenuItem_Click);
            // 
            // tripDetailsToolStripMenuItem
            // 
            this.tripDetailsToolStripMenuItem.Name = "tripDetailsToolStripMenuItem";
            this.tripDetailsToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.tripDetailsToolStripMenuItem.Text = "Trip details";
            this.tripDetailsToolStripMenuItem.Click += new System.EventHandler(this.tripDetailsToolStripMenuItem_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(652, 529);
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "Form1";
            this.Text = "Form1";
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem graphsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem averageScoreToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem tripDetailsToolStripMenuItem;
    }
}

