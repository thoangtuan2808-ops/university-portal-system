using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PortalAdminUI
{
    public class TimetableDTO
    {
        public int scheduleId { get; set; }
        public string subjectName { get; set; }
        public string roomName { get; set; }
        public string dayOfWeek { get; set; }
        public int shift { get; set; }
    }
}
