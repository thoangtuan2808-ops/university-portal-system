using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data;
using System.Data.SqlClient;

namespace PortalAdminUI
{
    public partial class frmAccountManagement : Form
    {
        string connectionString = @"Server=LAPTOP-GGRD5EEU;Database=UniversityPortal;Trusted_Connection=True;";
        public frmAccountManagement()
        {
            InitializeComponent();
        }
        private void Load_Data()
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                    string query = "SELECT UserID, UserName, Email, RoleID, IsActive, CreateAt FROM Users";
                    using (SqlCommand cmd = new SqlCommand(query, conn))
                    {
                        //SqlDataAdapter chở dữ liệu từ sql về c#
                        using (SqlDataAdapter adapter = new SqlDataAdapter(cmd))
                        {
                            DataTable dt = new DataTable(); //tạo khay rỗng
                            adapter.Fill(dt); //đổ dữ liệu vào khay
                            dgvUsers.DataSource = dt;
                        }
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Lỗi tải dữ liệu: " + ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }
        private void frmAccountManagement_Load(object sender, EventArgs e)
        {
            Load_Data();
        }

        private void làmMớiDanhSáchToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Load_Data();
            MessageBox.Show("Đã làm mới danh sách: ", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void contextMenuStrip1_Opening(object sender, CancelEventArgs e)
        {

        }

        private void thêmTàiKhoảnToolStripMenuItem_Click(object sender, EventArgs e)
        {
            frmAccountDetail frmAccountDetail = new frmAccountDetail();
            frmAccountDetail.ShowDialog();
            Load_Data();
        }

        private void khóaXóaTàiKhoảnToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (dgvUsers.CurrentRow != null)
            {
                int userID = Convert.ToInt32(dgvUsers.CurrentRow.Cells["UserID"].Value);
                string userName = dgvUsers.CurrentRow.Cells["UserName"].Value.ToString();
                DialogResult result = MessageBox.Show("Bạn có muốn xóa tài khoản [{userName}] không?", "Xác nhận xóa", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                if (result == DialogResult.Yes)
                {
                    string connectionString = @"Server=LAPTOP-GGRD5EEU; Database=UniversityPortal; Trusted_Connection=True";
                    using (SqlConnection conn = new SqlConnection(connectionString))
                    {
                        try
                        {
                            conn.Open();
                            string query = "UPDATE Users SET IsActive = 0 WHERE UserID = @UserID";
                            using (SqlCommand cmd = new SqlCommand(query, conn))
                            {
                                cmd.Parameters.AddWithValue("@UserID", userID);
                                cmd.ExecuteNonQuery();
                                MessageBox.Show("Đã khóa/xóa tài khoản!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                            }
                        }
                        catch (Exception ex)
                        {
                            MessageBox.Show("Lỗi khi khóa/xóa tài khoản!" + ex.Message, "Lỗi hệ thống!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }
                }
            }
            else
            {
                MessageBox.Show("Vui lòng chọn một tài khoản trong danh sách để khóa/xóa!", "Cảnh báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
        }

        private void cậpNhậtThôngTinToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (dgvUsers.CurrentRow != null) { 
                int selectedID = Convert.ToInt32(dgvUsers.CurrentRow.Cells["UserID"].Value);
                frmAccountDetail frmAccountDetail = new frmAccountDetail();
                frmAccountDetail.accID = selectedID;
                frmAccountDetail.ShowDialog();
                Load_Data();
            }
            else
            {
                MessageBox.Show("Vui lòng chọn một tài khoản trong danh sách để cập nhật!", "Cảnh báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
        }
    }
}
