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
    public partial class frmAccountDetail : Form
    {
        public int accID = 0; // =0 là thêm mới, >0 là update
        public frmAccountDetail()
        {
            InitializeComponent();
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void frmAccountDetail_Load(object sender, EventArgs e)
        {
            string connectionString = @"Server=LAPTOP-GGRD5EEU; Database=UniversityPortal; Trusted_Connection=True;";
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                string query = "SELECT RoleID, RoleName FROM Roles";
                SqlDataAdapter dataAdapter = new SqlDataAdapter(query, conn);
                DataTable dt = new DataTable();
                dataAdapter.Fill(dt);
                cboRole.DataSource = dt;
                cboRole.DisplayMember = "RoleName";
                cboRole.ValueMember = "RoleID";
                if (accID > 0)
                {
                    conn.Open();
                    string queryUser = "SELECT UserName, PasswordHash, Email, RoleID, IsActive FROM Users WHERE UserID=@ID";
                    using (SqlCommand cmd = new SqlCommand(queryUser, conn))
                    {
                        cmd.Parameters.AddWithValue("@ID", accID);
                        using (SqlDataReader reader = cmd.ExecuteReader())
                        {
                            if (reader.Read())
                            {
                                txtUserName.Text = reader["UserName"].ToString();
                                txtPassword.Text = reader["PasswordHash"].ToString();
                                txtEmail.Text = reader["Email"].ToString();
                                cboRole.SelectedValue = reader["RoleID"];
                                checkIsActive.Checked = Convert.ToBoolean(reader["IsActive"]);

                                txtUserName.Enabled = false;
                            }
                        }
                    }
                }
            }
        }

        private void btnSave_Click(object sender, EventArgs e)
        {
            string username = txtUserName.Text.Trim();
            string password = txtPassword.Text;
            string email = txtEmail.Text.Trim();
            int roleID = Convert.ToInt32(cboRole.SelectedValue);
            int isActive = checkIsActive.Checked ? 1 : 0;
            if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password) || string.IsNullOrEmpty(email)) { 
                MessageBox.Show("Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }
            string connectionString = @"Server=LAPTOP-GGRD5EEU; Database=UniversityPortal; Trusted_Connection=True";
            using (SqlConnection conn = new SqlConnection(connectionString)) {
                try
                {
                    conn.Open();
                    string query = "";
                    if (accID == 0)
                    {
                        query = "INSERT INTO Users (Username, PasswordHash, Email, RoleID, IsActive) VALUES (@User, @Pass, @Email, @Role, @Active)";
                    }
                    else 
                    {
                        query = "UPDATE Users SET UserName=@User, PasswordHash=@Pass, Email=@Email, RoleID=@Role, IsActive=@Active WHERE UserID=@ID";
                    }
                        using (SqlCommand cmd = new SqlCommand(query, conn))
                        {
                            cmd.Parameters.AddWithValue("@User", username);
                            cmd.Parameters.AddWithValue("@Pass", password);
                            cmd.Parameters.AddWithValue("@Email", email);
                            cmd.Parameters.AddWithValue("@Role", roleID);
                            cmd.Parameters.AddWithValue("@Active", isActive);
                            if (accID > 0)
                            {
                                cmd.Parameters.AddWithValue("@ID", accID);
                            }
                            cmd.ExecuteNonQuery(); //chèn
                            string act = (accID == 0) ? "Thêm" : "Cập nhật";
                            MessageBox.Show($"{act} tài khoản thành công!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                            this.Close();
                        }
                }
                catch (Exception ex) { 
                    MessageBox.Show("Lỗi khi thêm tài khoản!"+ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }
    }
}
