namespace PortalAdminUI
{
    partial class frmFacultiesAndMajorsManagement
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
            this.dgvFaMj = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvFaMj)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvFaMj
            // 
            this.dgvFaMj.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvFaMj.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvFaMj.Location = new System.Drawing.Point(0, 0);
            this.dgvFaMj.Name = "dgvFaMj";
            this.dgvFaMj.RowHeadersWidth = 51;
            this.dgvFaMj.RowTemplate.Height = 24;
            this.dgvFaMj.Size = new System.Drawing.Size(800, 450);
            this.dgvFaMj.TabIndex = 0;
            this.dgvFaMj.CellDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgvFaMj_CellDoubleClick);
            // 
            // frmFacultiesAndMajorsManagement
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dgvFaMj);
            this.Name = "frmFacultiesAndMajorsManagement";
            this.Text = "frmFacultiesAndMajorsManagement";
            this.Load += new System.EventHandler(this.frmFacultiesAndMajorsManagement_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvFaMj)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvFaMj;
    }
}