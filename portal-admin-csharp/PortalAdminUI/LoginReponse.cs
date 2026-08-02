using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PortalAdminUI
{
    internal class LoginReponse
    {
        public string status { get; set; }
        public string message { get; set; }
        public int roleId { get; set; }
        public string token { get; set; }
    }
}
