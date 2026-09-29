using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PortalAdminUI
{
    public partial class frmTimetables : Form
    {
        public frmTimetables()
        {
            InitializeComponent();
        }

        private async void frmTimetables_Load(object sender, EventArgs e)
        {
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    // 1. Gắn thẻ bài bảo mật JWT
                    client.DefaultRequestHeaders.Clear();
                    client.DefaultRequestHeaders.Add("Authorization", "Bearer " + Program.CurrentUserToken);

                    // 2. Gọi API Thời khóa biểu
                    string apiUrl = "http://localhost:8080/UniversityPortalBackend/api/admin/timetables";
                    HttpResponseMessage response = await client.GetAsync(apiUrl);

                    // 3. Xử lý kết quả trả về
                    if (response.IsSuccessStatusCode)
                    {
                        string jsonResponse = await response.Content.ReadAsStringAsync();

                        // Ánh xạ JSON vào class DTO
                        List<TimetableDTO> list = JsonConvert.DeserializeObject<List<TimetableDTO>>(jsonResponse);

                        // Đổ dữ liệu vào DataGridView
                        dgvTimetables.DataSource = list;
                    }
                    else
                    {
                        string errorDetail = await response.Content.ReadAsStringAsync();
                        MessageBox.Show($"Lỗi API: {response.StatusCode}\n{errorDetail}", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Lỗi kết nối máy chủ: " + ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
