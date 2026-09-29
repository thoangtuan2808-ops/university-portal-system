using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PortalAdminUI
{
    public class RegistrationDTO
    {
        public int registrationId { get; set; }
        public string studentName { get; set; }
        public string subjectName { get; set; }
        public string registrationDate { get; set; }
        public string status { get; set; }
    }
}
