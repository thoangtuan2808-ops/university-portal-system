using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

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

        private async void frmAccountDetail_Load(object sender, EventArgs e)
        {
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    // --- MẶC GIÁP JWT NGAY TỪ ĐẦU CHO MỌI REQUEST ---
                    client.DefaultRequestHeaders.Add("Authorization", "Bearer " + Program.CurrentUserToken);

                    // 1. GỌI API LẤY DANH SÁCH QUYỀN
                    HttpResponseMessage roleRes = await client.GetAsync("http://localhost:8080/UniversityPortalBackend/api/roles");
                    if (roleRes.IsSuccessStatusCode)
                    {
                        string roleJson = await roleRes.Content.ReadAsStringAsync();
                        var roles = JsonConvert.DeserializeObject<List<RoleDTO>>(roleJson);
                        // --- LUẬT CHỐNG TẠO PHẢN TRÊN GIAO DIỆN ---
                        // Nếu là Admin thường (Role = 1), dùng LING lọc bỏ quyền 4 và 1, chỉ giữ lại từ 2 trở đi
                        if (Program.CurrentUserRole == 1)
                        {
                            roles = roles.Where(r => r.roleId == 2 || r.roleId == 3).ToList();
                        }
                        cboRole.DataSource = roles;
                        cboRole.DisplayMember = "roleName";
                        cboRole.ValueMember = "roleId";
                    }

                    // 2. GỌI API LẤY CHI TIẾT TÀI KHOẢN (NẾU LÀ CHỨC NĂNG CẬP NHẬT)
                    if (accID > 0)
                    {
                        HttpResponseMessage userRes = await client.GetAsync($"http://localhost:8080/UniversityPortalBackend/api/users/detail?userId={accID}");
                        if (userRes.IsSuccessStatusCode)
                        {
                            string userJson = await userRes.Content.ReadAsStringAsync();
                            var user = JsonConvert.DeserializeObject<UserDTO>(userJson);

                            txtUserName.Text = user.userName;
                            txtUserName.Enabled = false; // Khóa mỏ neo, cấm đổi tên đăng nhập

                            txtEmail.Text = user.email;
                            cboRole.SelectedValue = user.roleId;
                            checkIsActive.Checked = user.isActive;

                            // Mật khẩu để trống, nếu Admin muốn đổi thì gõ vào, không thì chốt chặn lúc nãy sẽ yêu cầu xác nhận.
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Lỗi nạp dữ liệu API: " + ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async void btnSave_Click(object sender, EventArgs e)
        {
            try
            {
                // --- CHỐT CHẶN BẢO MẬT MẬT KHẨU ---
                // NẾU là Thêm Mới (accID == 0) MÀ để trống Password -> Báo lỗi
                if (accID == 0 && string.IsNullOrWhiteSpace(txtPassword.Text))
                {
                    MessageBox.Show("Vui lòng nhập mật khẩu mới hoặc nhập lại mật khẩu cũ trước khi lưu!",
                                    "Cảnh báo bảo mật",
                                    MessageBoxButtons.OK,
                                    MessageBoxIcon.Warning);
                    txtPassword.Focus();
                    return; // Cắt luồng chạy, không cho gọi xuống HttpClient
                }
                using (HttpClient client = new HttpClient()) {
                    // --- MẶC GIÁP JWT ---
                    client.DefaultRequestHeaders.Add("Authorization", "Bearer " + Program.CurrentUserToken);
                    var content = new FormUrlEncodedContent(new[]
                    {
                        new KeyValuePair<string, string>("userId", accID.ToString()),
                        new KeyValuePair<string, string>("username", txtUserName.Text.Trim()),
                        new KeyValuePair<string, string>("password", txtPassword.Text.Trim()),
                        new KeyValuePair<string, string>("email", txtEmail.Text.Trim()),
                        new KeyValuePair<string, string>("roleId", cboRole.SelectedValue.ToString()),
                        new KeyValuePair<string, string>("isActive", checkIsActive.Checked ? "1" : "0")
                    });
                    HttpResponseMessage response = await client.PostAsync("http://localhost:8080/UniversityPortalBackend/api/users/save", content);
                    if (response.IsSuccessStatusCode) {
                        MessageBox.Show("Lưu dữ liệu vào tài khoản thành công!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        this.DialogResult = DialogResult.OK;
                        this.Close();
                    }
                    else
                    {
                        string error = await response.Content.ReadAsStringAsync();
                        MessageBox.Show($"Mã HTTP: {response.StatusCode}\nChi tiết: {error}", "Bắt mạch lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);

                    }
                }
            }
            catch (Exception ex) { 
                MessageBox.Show("Lỗi kết nối API: "+ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }            
        }

        private void cboRole_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}
