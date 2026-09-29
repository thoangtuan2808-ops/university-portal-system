namespace PortalAdminUI
{
    partial class frmCourseClasses
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
            this.dgvCourseClasses = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvCourseClasses)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvCourseClasses
            // 
            this.dgvCourseClasses.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvCourseClasses.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvCourseClasses.Location = new System.Drawing.Point(0, 0);
            this.dgvCourseClasses.Name = "dgvCourseClasses";
            this.dgvCourseClasses.RowHeadersWidth = 51;
            this.dgvCourseClasses.RowTemplate.Height = 24;
            this.dgvCourseClasses.Size = new System.Drawing.Size(800, 450);
            this.dgvCourseClasses.TabIndex = 0;
            // 
            // frmCourseClasses
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dgvCourseClasses);
            this.Name = "frmCourseClasses";
            this.Text = "frmCourseClasses";
            this.Load += new System.EventHandler(this.frmCourseClasses_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvCourseClasses)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvCourseClasses;
    }
}