namespace PortalAdminUI
{
    partial class frmRegistrations
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
            this.dgvRegistrations = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvRegistrations)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvRegistrations
            // 
            this.dgvRegistrations.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvRegistrations.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvRegistrations.Location = new System.Drawing.Point(0, 0);
            this.dgvRegistrations.Name = "dgvRegistrations";
            this.dgvRegistrations.RowHeadersWidth = 51;
            this.dgvRegistrations.RowTemplate.Height = 24;
            this.dgvRegistrations.Size = new System.Drawing.Size(800, 450);
            this.dgvRegistrations.TabIndex = 0;
            // 
            // frmRegistrations
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dgvRegistrations);
            this.Name = "frmRegistrations";
            this.Text = "frmRegistrations";
            this.Load += new System.EventHandler(this.frmRegistrations_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvRegistrations)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvRegistrations;
    }
}