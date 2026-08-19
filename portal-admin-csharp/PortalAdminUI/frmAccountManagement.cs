using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System.Collections.Generic;
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
        // Khai báo một biến nội bộ để nhớ xem Form này đang quản lý ai (1: QTV, 2: GV, 3: SV)
        private int currentRoleType = 0;
        public frmAccountManagement(int RoleType)
        {
            InitializeComponent();
            currentRoleType = RoleType;
            // Cất số vừa nhận vào biến nội bộ

            // Đổi tiêu đề cửa sổ Form cho chuyên nghiệp
            if (RoleType == 1) this.Text = "QUẢN LÝ TÀI KHOẢN QUẢN TRỊ VIÊN";
            else if (RoleType == 2) this.Text = "QUẢN LÝ TÀI KHOẢN GIẢNG VIÊN";
            else if (RoleType == 3) this.Text = "QUẢN LÝ TÀI KHOẢN SINH VIÊN";
        }
        private async void Load_Data()
        {
            {
                try
                {
                    using (HttpClient client = new HttpClient()) {
                        // --- MẶC GIÁP JWT ---
                        client.DefaultRequestHeaders.Add("Authorization", "Bearer " + Program.CurrentUserToken);
                        client.DefaultRequestHeaders.Add("X-Role-Id", currentRoleType.ToString());
                        // --- NỐI THAM SỐ VÀO LINK API ---
                        string apiUrl = $"http://localhost:8080/UniversityPortalBackend/api/users";
                   
                        HttpResponseMessage response = await client.GetAsync("http://localhost:8080/UniversityPortalBackend/api/users");
                        if (response.IsSuccessStatusCode) { 
                            string jsonReponse = await response.Content.ReadAsStringAsync();
                            List<UserDTO> userList = JsonConvert.DeserializeObject<List<UserDTO>>(jsonReponse);
                            dgvUsers.DataSource = userList;
                        }
                        else
                        {
                            string errorDetail = await response.Content.ReadAsStringAsync();
                            MessageBox.Show($"Máy chủ từ chối!\nMã HTTP: {response.StatusCode}\nLink đã gọi: {apiUrl}\nChi tiết: {errorDetail}", "Bắt mạch lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }
                }
                catch(Exception ex) {
                    MessageBox.Show("Lỗi kết nối API: "+ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK,MessageBoxIcon.Error);
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

        private async void khóaXóaTàiKhoảnToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (dgvUsers.CurrentRow != null)
            {
                int userId = Convert.ToInt32(dgvUsers.CurrentRow.Cells["UserId"].Value);
                string userName = dgvUsers.CurrentRow.Cells["userName"].Value.ToString();
                DialogResult result = MessageBox.Show($"Bạn có muốn xóa tài khoản [{userName}] không?", "Xác nhận xóa", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                if (result == DialogResult.Yes)
                {
                    {
                        try
                        {
                            //gói thông tin id để ném sang java
                            using (HttpClient client = new HttpClient()) {
                                client.DefaultRequestHeaders.Add("Authorization", "Bearer " + Program.CurrentUserToken);
                                var content = new FormUrlEncodedContent(new[]
                                {
                                    new KeyValuePair<string, string>("userId", userId.ToString())
                                });

                                //bắn post request đến API lock
                                HttpResponseMessage response = await client.PostAsync("http://localhost:8080/UniversityPortalBackend/api/users/lock", content);
                                if (response.IsSuccessStatusCode) { 
                                    MessageBox.Show("Khóa tài khoản thành công!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information );
                                    Load_Data();
                                }
                                else
                                {
                                    // Nếu bị Java chặn (luật chống tạo phản), in lỗi ra để Admin biết
                                    string errorMsg = await response.Content.ReadAsStringAsync();
                                    MessageBox.Show("Lỗi từ máy chủ khi khóa tài khoản.", "Lỗi", MessageBoxButtons.OK,MessageBoxIcon.Error );
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            MessageBox.Show("Lỗi kết nối API: "+ ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error );
                        }
                    }
                }
            }
            else
            {
               MessageBox.Show("Vui lòng chọn một tài khoản trong danh sách để khóa!","Cảnh báo", MessageBoxButtons.OK, MessageBoxIcon.Warning );
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

        private void dgvUsers_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}
