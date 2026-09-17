using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PortalAdminUI
{
    public partial class frmMain : Form
    {
        public frmMain()
        {
            InitializeComponent();
        }

        private void đăngXuấtToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void quảnLýTàiKhoảnToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void frmMain_Load(object sender, EventArgs e)
        {
            // Nếu người đăng nhập là Admin thường (RoleID = 1)
            if (Program.CurrentUserRole == 1)
            {
                // Ẩn menu Quản lý Quản trị viên đi.
                // (Tên biến 'quảnLýTàiKhoảnQTVToolStripMenuItem' phải khớp với tên trong Properties của bạn)
                quảnLýTàiKhoảnQTVToolStripMenuItem.Visible = false;
            }
            // Nếu là Super Admin (4) thì bỏ qua, mặc định Visible đã là true nên sẽ thấy hết.
        }

        private void hệThốngToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void quảnLýTàiKhoảnQTVToolStripMenuItem_Click(object sender, EventArgs e)
        {
            // Truyền số 1 sang Form Quản lý (Ngày mai Form Quản lý sẽ nhận số này)
            frmAccountManagement frm = new frmAccountManagement(1);
            frm.ShowDialog();
        }

        private void quảnLýTàiKhoảnGVToolStripMenuItem_Click(object sender, EventArgs e)
        {
            // Truyền số 2 sang
            frmAccountManagement frm = new frmAccountManagement(2);
            frm.ShowDialog();
        }

        private void quảnLýTàiKhoảnSVToolStripMenuItem_Click(object sender, EventArgs e)
        {
            // Truyền số 3 sang
            frmAccountManagement frm = new frmAccountManagement(3);
            frm.ShowDialog();
        }

        private void sinhViênToolStripMenuItem_Click(object sender, EventArgs e)
        {
            frmStudentProfile frm = new frmStudentProfile();
            frm.ShowDialog();
        }

        private void giảngViênToolStripMenuItem_Click(object sender, EventArgs e)
        {
            frmTeacherProfile frm = new frmTeacherProfile();
            frm.ShowDialog();
        }

        private void nhânViênToolStripMenuItem_Click(object sender, EventArgs e)
        {
            FrmStaffProfile frm = new FrmStaffProfile();
            frm.ShowDialog();
        }

        private void quảnLýConNgườiToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void quảnLíPhòngBanToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }
    }
}
