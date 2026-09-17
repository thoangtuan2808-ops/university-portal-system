using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PortalAdminUI
{
    public class StaffProfileDTO
    {
        public int staffId {  get; set; }
        public int userID { get; set; }
        public string departmentName { get; set; }
        public string fullName { get; set; }
        public string email { get; set; }
        public string dob { get; set; }
        public string address { get; set; }
        public string phoneNumber { get; set; }
    }
}
