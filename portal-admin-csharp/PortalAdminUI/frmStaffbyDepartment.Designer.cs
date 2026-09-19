namespace PortalAdminUI
{
    partial class frmStaffbyDepartment
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
            this.dgvStaffbyDepartment = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvStaffbyDepartment)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvStaffbyDepartment
            // 
            this.dgvStaffbyDepartment.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvStaffbyDepartment.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvStaffbyDepartment.Location = new System.Drawing.Point(0, 0);
            this.dgvStaffbyDepartment.Name = "dgvStaffbyDepartment";
            this.dgvStaffbyDepartment.RowHeadersWidth = 51;
            this.dgvStaffbyDepartment.RowTemplate.Height = 24;
            this.dgvStaffbyDepartment.Size = new System.Drawing.Size(800, 450);
            this.dgvStaffbyDepartment.TabIndex = 0;
            // 
            // frmStaffbyDepartment
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dgvStaffbyDepartment);
            this.Name = "frmStaffbyDepartment";
            this.Text = "frmStaffbyDepartment";
            this.Load += new System.EventHandler(this.frmStaffbyDepartment_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvStaffbyDepartment)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvStaffbyDepartment;
    }
}