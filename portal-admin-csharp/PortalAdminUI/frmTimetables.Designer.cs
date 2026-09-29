namespace PortalAdminUI
{
    partial class frmTimetables
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
            this.dgvTimetables = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvTimetables)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvTimetables
            // 
            this.dgvTimetables.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvTimetables.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvTimetables.Location = new System.Drawing.Point(0, 0);
            this.dgvTimetables.Name = "dgvTimetables";
            this.dgvTimetables.RowHeadersWidth = 51;
            this.dgvTimetables.RowTemplate.Height = 24;
            this.dgvTimetables.Size = new System.Drawing.Size(800, 450);
            this.dgvTimetables.TabIndex = 0;
            // 
            // frmTimetables
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dgvTimetables);
            this.Name = "frmTimetables";
            this.Text = "frmTimetables";
            this.Load += new System.EventHandler(this.frmTimetables_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvTimetables)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvTimetables;
    }
}