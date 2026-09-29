using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PortalAdminUI
{
    public class CourseClassDTO
    {
        public int courseClassId { get; set; }
        public string subjectName { get; set; }
        public string semesterName { get; set; }
        public string teacherName { get; set; }
        public int maxCapacity { get; set; }
    }
}
