using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PortalAdminUI
{
    public partial class DangNhap : Form
    {
        public DangNhap()
        {
            InitializeComponent();
        }

        private async void guna2Button1_Click(object sender, EventArgs e)
        {
            string username = txtUsername.Text.Trim();
            string password = txtPassword.Text.Trim();

            if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
            {
                MessageBox.Show("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Cảnh báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }
            username = Regex.Replace(username, @"\s+", "");//xóa khoảng trắng tàng hình
            if (!Regex.IsMatch(username, @"^[a-zA-Z0-9_]+$"))
            {
                MessageBox.Show("Tên đăng nhập chứa ký tự không hợp lệ!", "Lỗi bảo mật", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    var content = new FormUrlEncodedContent(new[]
                    {
                        new KeyValuePair<string, string>("username", username),
                        new KeyValuePair<string, string> ("password", password)
                    });
                    HttpResponseMessage reponse = await client.PostAsync("http://localhost:8080/UniversityPortalBackend/api/login", content);
                    string jsonReponse = await reponse.Content.ReadAsStringAsync();
                    LoginReponse result = JsonConvert.DeserializeObject<LoginReponse>(jsonReponse);
                    if (result != null && result.status == "success")
                    {
                        // --- CHỐT CHẶN 1: KIỂM TRA QUYỀN ---
                        if (result.roleId == 4 || result.roleId == 1)
                        {
                            // 1. Cất thẻ Token vào "ví" Program
                            Program.CurrentUserToken = result.token;
                            Program.CurrentUserRole = result.roleId; // <--- CẤT CHỨC VỤ VÀO ĐÂY

                            // 2. Mở cửa cho Admin
                            MessageBox.Show($"Đăng nhập thành công! Mã quyền: {result.roleId}", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                            frmMain frmMain = new frmMain();
                            this.Hide();
                            frmMain.ShowDialog();
                            this.Close();
                        }
                        else
                        {
                            // Đá văng Sinh viên/Giảng viên
                            MessageBox.Show("Cảnh báo: Tài khoản của bạn không có quyền truy cập hệ thống Quản trị!", "Truy cập bị từ chối", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }
                    else
                    {
                        string errorMsg = result != null ? result.message : "Đăng nhập thất bại";
                        MessageBox.Show(errorMsg, "Lỗi đăng nhập", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Lỗi kết nối máy chủ API: " + ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
