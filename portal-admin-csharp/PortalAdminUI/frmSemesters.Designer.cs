namespace PortalAdminUI
{
    partial class frmSemesters
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
            this.dgvSemesters = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvSemesters)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvSemesters
            // 
            this.dgvSemesters.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvSemesters.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvSemesters.Location = new System.Drawing.Point(0, 0);
            this.dgvSemesters.Name = "dgvSemesters";
            this.dgvSemesters.RowHeadersWidth = 51;
            this.dgvSemesters.RowTemplate.Height = 24;
            this.dgvSemesters.Size = new System.Drawing.Size(800, 450);
            this.dgvSemesters.TabIndex = 0;
            // 
            // frmSemesters
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dgvSemesters);
            this.Name = "frmSemesters";
            this.Text = "frmSemesters";
            this.Load += new System.EventHandler(this.frmSemesters_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvSemesters)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvSemesters;
    }
}