package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

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
        	// --- 1. CHỐT BẢO MẬT JWT (Đã ráp code thật) ---
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\":\"Thiếu Token bảo mật!\"}");
                out.flush();
                return;
            }
            
            String token = authHeader.substring(7);
            Algorithm algorithm = Algorithm.HMAC256("ChuoiKhoaBiMatCuaRiengBan_KhongDuocDeLo");
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("UniversityPortal").build();
            DecodedJWT decodedJWT = verifier.verify(token);
            
            // API lấy chi tiết để sửa trên WinForms -> Chỉ Role 4 và 1 được xài
            int tokenRoleId = decodedJWT.getClaim("roleId").asInt();
            if (tokenRoleId != 4 && tokenRoleId != 1) { 
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
                out.print("{\"error\":\"Bạn không có quyền xem thông tin này!\"}");
                out.flush();
                return; 
            }

            // --- 2. LẤY DỮ LIỆU ---
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
            out.print("{\"error\":\"Lỗi xác thực hoặc dữ liệu không hợp lệ\"}");
        }
        out.flush();
    }
}
