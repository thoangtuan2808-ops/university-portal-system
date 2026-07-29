using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace PortalAdminUI
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            string username = txtUsername.Text.Trim();
            string password = txtPassword.Text.Trim();

            if(string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password)) {
                MessageBox.Show("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Cảnh báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }
            username = Regex.Replace(username, @"\s+", "");//xóa khoảng trắng tàng hình
            if (!Regex.IsMatch(username, @"^[a-zA-Z0-9_]+$"))
            {
                MessageBox.Show("Tên đăng nhập chứa ký tự không hợp lệ!", "Lỗi bảo mật", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            string connectionString = @"Server=LAPTOP-GGRD5EEU;Database=UniversityPortal;Trusted_Connection=True;";

            using (SqlConnection conn = new SqlConnection(connectionString)) {
                try
                {
                    conn.Open();
                    //query raw test luồng
                    string query = "SELECT RoleID FROM Users WHERE Username = @Username AND IsActive=1";
                    using (SqlCommand cmd = new SqlCommand(query, conn))
                    {
                        //tham số para an toàn
                        cmd.Parameters.AddWithValue("@Username", username);
                        object result = cmd.ExecuteScalar();
                        if (result != null) {
                            int roleID = Convert.ToInt32(result);
                            MessageBox.Show($"Đăng nhập thành công! Mã quyền: {roleID}", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        }
                        else
                        {
                            MessageBox.Show("Sai tài khoản, mật khẩu hoặc tài khoản đã bị khóa!", "Lỗi đăng nhập", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }
                }
                catch (Exception ex) 
                {
                    MessageBox.Show("Lỗi kết nối CSDL: " + ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error); 
                }
            }
        }
    }
}
