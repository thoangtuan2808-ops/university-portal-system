package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;

@WebServlet("/api/users/detail")
public class UserDetailController extends HttpServlet {
    private UserService userService;
    
    public void init() { userService = new UserService(); }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // --- CHỐT BẢO MẬT JWT (Copy y hệt từ UserSaveController sang đây) ---
        // Nếu token hợp lệ thì mới cho chạy tiếp đoạn code bên dưới...
        
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            User u = userService.getUserById(userId);
            
            if (u != null) {
                out.print("{\"userId\":" + u.getUserId() + 
                          ", \"userName\":\"" + u.getUserName() + 
                          "\", \"email\":\"" + u.getEmail() + 
                          "\", \"roleId\":" + u.getRoleId() + 
                          ", \"isActive\":" + u.isActive() + "}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Không tìm thấy tài khoản\"}");
            }
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        out.flush();
    }
}
