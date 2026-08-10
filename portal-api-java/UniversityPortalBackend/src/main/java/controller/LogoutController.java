package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.StatsManager;

@WebServlet("/api/logout")
public class LogoutController extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Lấy Role ID từ trình duyệt gửi lên để biết ai vừa đăng xuất
            String roleIdStr = request.getParameter("roleId");
            
            if (roleIdStr != null) {
                int roleId = Integer.parseInt(roleIdStr);
                
                // Gọi StatsManager để TRỪ số lượng
                if (roleId == 3) {
                    StatsManager.removeStudent();
                    System.out.println("🔽 Một sinh viên vừa đăng xuất.");
                } else if (roleId == 2) {
                    StatsManager.removeTeacher();
                    System.out.println("🔽 Một giảng viên vừa đăng xuất.");
                }
            }
            
            // Trả về thông báo thành công
            out.print("{\"status\": \"success\"}");
            
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\": \"error\"}");
        } finally {
            out.flush();
        }
    }
}
