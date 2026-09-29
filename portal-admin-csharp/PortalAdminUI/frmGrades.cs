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
    public partial class frmGrades : Form
    {
        public frmGrades()
        {
            InitializeComponent();
        }

        private async void frmGrades_Load(object sender, EventArgs e)
        {
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Clear();
                    client.DefaultRequestHeaders.Add("Authorization", "Bearer " + Program.CurrentUserToken);

                    string apiUrl = "http://localhost:8080/UniversityPortalBackend/api/admin/grades";
                    HttpResponseMessage response = await client.GetAsync(apiUrl);

                    if (response.IsSuccessStatusCode)
                    {
                        string jsonResponse = await response.Content.ReadAsStringAsync();
                        List<GradeDTO> list = JsonConvert.DeserializeObject<List<GradeDTO>>(jsonResponse);
                        dgvGrades.DataSource = list; // Giả sử DataGridView tên là dgvGrades
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
                MessageBox.Show("Lỗi kết nối: " + ex.Message, "Lỗi hệ thống", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
