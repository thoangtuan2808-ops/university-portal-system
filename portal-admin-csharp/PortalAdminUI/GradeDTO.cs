using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PortalAdminUI
{
    public class GradeDTO
    {
        public int gradeId { get; set; }
        public string studentName { get; set; }
        public string subjectName { get; set; }
        public double midtermScore { get; set; }
        public double finalScore { get; set; }
    }
}
