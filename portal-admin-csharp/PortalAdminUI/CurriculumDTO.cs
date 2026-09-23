using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PortalAdminUI
{
    public class CurriculumDTO
    {
        public int CurriculumID {  get; set; }
        public string MajorName { get; set; }
        public string SubjectName { get; set; }
        public int Credits { get; set; }
        public int ExpectedSemester { get; set; }
    }
}
