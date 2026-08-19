using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PortalAdminUI
{
    internal static class Program
    {
        // Khai báo 2 chiếc "ví" toàn cục để cất thẻ bài và chức vụ
        public static string CurrentUserToken = "";
        public static int CurrentUserRole = -1; // <--- THÊM DÒNG NÀY
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}
