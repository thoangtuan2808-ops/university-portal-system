using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PortalAdminUI
{
    public class UserDTO
    {
        public int userId { get; set; }
        public string userName { get; set; }
        public string email { get; set; }
        public int roleId { get; set; }
        public bool isActive { get; set; }
        public string createAt { get; set; }
       
    }
}
