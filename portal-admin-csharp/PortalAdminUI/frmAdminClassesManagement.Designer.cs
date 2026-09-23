namespace PortalAdminUI
{
    partial class frmAdminClassesManagement
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
            this.dgvAdminclass = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvAdminclass)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvAdminclass
            // 
            this.dgvAdminclass.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvAdminclass.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvAdminclass.Location = new System.Drawing.Point(0, 0);
            this.dgvAdminclass.Name = "dgvAdminclass";
            this.dgvAdminclass.RowHeadersWidth = 51;
            this.dgvAdminclass.RowTemplate.Height = 24;
            this.dgvAdminclass.Size = new System.Drawing.Size(800, 450);
            this.dgvAdminclass.TabIndex = 0;
            // 
            // AdminClassesManagement
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dgvAdminclass);
            this.Name = "AdminClassesManagement";
            this.Text = "AdminClassesManagement";
            this.Load += new System.EventHandler(this.AdminClassesManagement_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvAdminclass)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvAdminclass;
    }
}