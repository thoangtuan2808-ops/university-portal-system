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
    public partial class frmStaffbyDepartment : Form
    {
        private string _selectedDepartmentName;
        public frmStaffbyDepartment(string departmentName)
        {
            InitializeComponent();
            _selectedDepartmentName = departmentName;

            // Đổi tên tiêu đề Form cho trực quan
            this.Text = $"Danh sách nhân viên - {_selectedDepartmentName}";
        }

        private async void frmStaffbyDepartment_Load(object sender, EventArgs e)
        {
                try
                {
                    using (HttpClient client = new HttpClient())
                    {
                        // 1. CHỐT CHẶN BẢO MẬT: Gắn thẻ bài JWT
                        client.DefaultRequestHeaders.Clear();
                        client.DefaultRequestHeaders.Add("Authorization", "Bearer " + Program.CurrentUserToken);

                        // 2. GỌI API
                        string apiUrl = $"http://localhost:8080/UniversityPortalBackend/api/admin/staffs";
                        HttpResponseMessage response = await client.GetAsync(apiUrl);

                    if (response.IsSuccessStatusCode)
                    {
                        // ĐỌC VÀ ÉP KIỂU JSON
                        string jsonResponse = await response.Content.ReadAsStringAsync();
                        List<StaffProfileDTO> allStaffs = JsonConvert.DeserializeObject<List<StaffProfileDTO>>(jsonResponse);

                        // --- ĐIỂM CHỈNH SỬA QUAN TRỌNG ---
                        // Lọc ra những người có departmentName trùng với phòng ban được chọn
                        var filteredStaffs = allStaffs
                                            .Where(s => s.departmentName == _selectedDepartmentName)
                                            .ToList();

                        // Đổ dữ liệu ĐÃ LỌC vào DataGridView
                        dgvStaffbyDepartment.DataSource = filteredStaffs;
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
    }
}
