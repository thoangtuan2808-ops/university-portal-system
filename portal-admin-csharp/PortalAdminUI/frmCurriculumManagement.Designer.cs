namespace PortalAdminUI
{
    partial class frmCurriculumManagement
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
            this.dgvCurriculum = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvCurriculum)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvCurriculum
            // 
            this.dgvCurriculum.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvCurriculum.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvCurriculum.Location = new System.Drawing.Point(0, 0);
            this.dgvCurriculum.Name = "dgvCurriculum";
            this.dgvCurriculum.RowHeadersWidth = 51;
            this.dgvCurriculum.RowTemplate.Height = 24;
            this.dgvCurriculum.Size = new System.Drawing.Size(800, 450);
            this.dgvCurriculum.TabIndex = 0;
            // 
            // CurriculumManagement
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dgvCurriculum);
            this.Name = "CurriculumManagement";
            this.Text = "CurriculumManagement";
            this.Load += new System.EventHandler(this.CurriculumManagement_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvCurriculum)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvCurriculum;
    }
}