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
        public frmAccountManagement()
        {
            InitializeComponent();
        }
        private async void Load_Data()
        {
            {
                try
                {
                    using (HttpClient client = new HttpClient()) {
                        HttpResponseMessage reponse = await client.GetAsync("http://localhost:8080/UniversityPortalBackend/api/users");
                        if (reponse.IsSuccessStatusCode) { 
                            string jsonReponse = await reponse.Content.ReadAsStringAsync();
                            List<UserDTO> userList = JsonConvert.DeserializeObject<List<UserDTO>>(jsonReponse);
                            dgvUsers.DataSource = userList;
                        }
                        else
                        {
                            MessageBox.Show("Không thể lấy dữ liệu từ máy chủ.", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
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

        private async void khóaXóaTàiKhoảnToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (dgvUsers.CurrentRow != null)
            {
                int userId = Convert.ToInt32(dgvUsers.CurrentRow.Cells["UserId"].Value);
                string userName = dgvUsers.CurrentRow.Cells["userName"].Value.ToString();
                DialogResult result = MessageBox.Show("Bạn có muốn xóa tài khoản [{userName}] không?", "Xác nhận xóa", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                if (result == DialogResult.Yes)
                {
                    {
                        try
                        {
                            //gói thông tin id để ném sang java
                            using (HttpClient client = new HttpClient()) {
                                var content = new FormUrlEncodedContent(new[]
                                {
                                    new KeyValuePair<string, string>("userId", userId.ToString())
                                });

                                //bắn post request đến API lock
                                HttpResponseMessage reponse = await client.PostAsync("http://localhost:8080/UniversityPortalBackend/api/users/lock", content);
                                if (reponse.IsSuccessStatusCode) { 
                                    MessageBox.Show("Khóa tài khoản thành công!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information );
                                    Load_Data();
                                }
                                else
                                {
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
