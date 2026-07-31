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

// Khai báo đường dẫn API để bên ngoài gọi vào
@WebServlet("/api/login")
public class UserController extends HttpServlet {
    
    private UserService userService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo bộ não Service khi API bắt đầu chạy
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Thiết lập cấu hình để đọc và trả về tiếng Việt (UTF-8)
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Lấy dữ liệu Username và Password từ client (C# / PHP / Postman) gửi lên
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // Gọi logic kiểm tra Đăng nhập
        User loginUser = userService.login(user, pass);

        // Trả kết quả về cho Client dưới dạng chuỗi chuẩn JSON
        if (loginUser != null) {
            out.print("{\"status\": \"success\", \"message\": \"Đăng nhập thành công\", \"roleId\": " + loginUser.getRoleId() + "}");
        } else {
            out.print("{\"status\": \"error\", \"message\": \"Sai tài khoản hoặc mật khẩu hoặc bị khóa\"}");
        }
        out.flush();
    }
}
