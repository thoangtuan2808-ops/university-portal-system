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
    public partial class frmDeparmentManagement : Form
    {
        public frmDeparmentManagement()
        {
            InitializeComponent();
        }

        private async void frmDeparmentManagement_Load(object sender, EventArgs e)
        {
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    // 1. CHỐT CHẶN BẢO MẬT: Gắn thẻ bài JWT
                    client.DefaultRequestHeaders.Clear();
                    client.DefaultRequestHeaders.Add("Authorization", "Bearer " + Program.CurrentUserToken);

                    // 2. GỌI API
                    string apiUrl = $"http://localhost:8080/UniversityPortalBackend/api/admin/departments";
                    HttpResponseMessage response = await client.GetAsync(apiUrl);

                    if (response.IsSuccessStatusCode)
                    {
                        // 3. ĐỌC VÀ ÉP KIỂU JSON
                        string jsonResponse = await response.Content.ReadAsStringAsync();
                        List<DepartmentDTO> departmentDTOs = JsonConvert.DeserializeObject<List<DepartmentDTO>>(jsonResponse);
                        dgvDepartment.DataSource = departmentDTOs;
                        dgvDepartment.Columns["departmentId"].Visible = false;
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
        private List<StaffProfileDTO> _allStaffs = new List<StaffProfileDTO>();
        private void dgvDepartment_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0) {
                string selectedDepartName = dgvDepartment.Rows[e.RowIndex].Cells["DepartmentName"].Value.ToString();
                var fillteredStaffs = _allStaffs.Where(s => s.departmentName == selectedDepartName).ToList();
                frmStaffbyDepartment frmStaffbyDepartment = new frmStaffbyDepartment(selectedDepartName);
                frmStaffbyDepartment.ShowDialog();
            }
        }
    }
}
