using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PortalAdminUI
{
    public class TeacherProfileDTO
    {
        public int teacherId { get; set; }
        public int userId { get; set; }
        public string facultyName { get; set; }
        public string degree { get; set; }
        public string fullName { get; set; }
        public string email { get; set; }
        public string dob { get; set; }
        public string address { get; set; }
        public string phoneNumber { get; set; }
    }
}
