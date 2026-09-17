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
    public partial class frmStudentProfile : Form
    {
        public frmStudentProfile()
        {
            InitializeComponent();
        }

        private async void frmStudentProfile_Load(object sender, EventArgs e)
        {
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    // 1. CHỐT CHẶN BẢO MẬT: Gắn thẻ bài JWT
                    client.DefaultRequestHeaders.Clear();
                    client.DefaultRequestHeaders.Add("Authorization", "Bearer " + Program.CurrentUserToken);

                    // 2. GỌI API
                    string apiUrl = $"http://localhost:8080/UniversityPortalBackend/api/admin/students";
                    HttpResponseMessage response = await client.GetAsync(apiUrl);

                    if (response.IsSuccessStatusCode)
                    {
                        // 3. ĐỌC VÀ ÉP KIỂU JSON
                        string jsonResponse = await response.Content.ReadAsStringAsync();
                        List<StudentProfileDTO> studentProfileDTOs = JsonConvert.DeserializeObject<List<StudentProfileDTO>>(jsonResponse);
                        dgvStudents.DataSource = studentProfileDTOs;
                    }
                    else
                    {
                        string errorDetail = await response.Content.ReadAsStringAsync();
                        MessageBox.Show($"Không thể tải hồ sơ!\nMã HTTP: {response.StatusCode}\nChi tiết: {errorDetail}", "Lỗi API", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Lỗi kết nối máy chủ: " + ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}
