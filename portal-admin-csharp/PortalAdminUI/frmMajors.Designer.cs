namespace PortalAdminUI
{
    partial class frmMajors
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
            this.dgvMajors = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvMajors)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvMajors
            // 
            this.dgvMajors.AllowDrop = true;
            this.dgvMajors.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvMajors.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvMajors.Location = new System.Drawing.Point(0, 0);
            this.dgvMajors.Name = "dgvMajors";
            this.dgvMajors.RowHeadersWidth = 51;
            this.dgvMajors.RowTemplate.Height = 24;
            this.dgvMajors.Size = new System.Drawing.Size(800, 450);
            this.dgvMajors.TabIndex = 0;
            // 
            // frmMajors
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dgvMajors);
            this.Name = "frmMajors";
            this.Text = "frmMajors";
            this.Load += new System.EventHandler(this.frmMajors_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvMajors)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvMajors;
    }
}