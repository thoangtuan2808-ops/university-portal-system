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
@WebServlet("/api/users/lock")
public class UserLockController extends HttpServlet {
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		userService = new UserService();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//Sửa ngày 18/08/2026: Admin (1) không được khóa Super Admin (4) hoặc Admin (1)
		try {
			// --- 1. KIỂM TRA THẺ BÀI (JWT) ---
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"status\":\"error\",\"message\":\"Thiếu Token bảo mật!\"}");
                out.flush();
                return;
            }
            String token = authHeader.substring(7);
            Algorithm algorithm = Algorithm.HMAC256("ChuoiKhoaBiMatCuaRiengBan_KhongDuocDeLo");
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("UniversityPortal").build();
            DecodedJWT decodedJWT = verifier.verify(token);
            int tokenRoleId = decodedJWT.getClaim("roleId").asInt();
            if (tokenRoleId != 4 && tokenRoleId != 1) { 
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
                out.print("{\"status\":\"error\", \"message\":\"Bạn không có quyền khóa tài khoản!\"}");
                out.flush();
                return; 
            }
            // --- 2. LẤY ID TÀI KHOẢN CẦN KHÓA VÀ KIỂM TRA LUẬT ---
            int userId = Integer.parseInt(request.getParameter("userId"));
            User targetUser = userService.getUserById(userId);
            
            if (targetUser == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"status\":\"error\", \"message\":\"Không tìm thấy tài khoản để khóa\"}");
                out.flush();
                return;
            }
            // CHỐNG TẠO PHẢN: Admin (1) không được khóa Super Admin (4) hoặc Admin (1)
            if (tokenRoleId == 1 && (targetUser.getRoleId() == 4 || targetUser.getRoleId() == 1)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print("{\"status\":\"error\", \"message\":\"Admin không được phép khóa tài khoản quản trị viên khác!\"}");
                out.flush();
                return;
            }
            // --- 3. THỰC HIỆN KHÓA (Đã vá lỗi cú pháp JSON) ---
            boolean success = userService.lockUser(userId);
            if(success) {
                out.print("{\"status\":\"success\",\"message\":\"Đã khóa tài khoản thành công\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"status\":\"error\", \"message\":\"Không thể khóa tài khoản này\"}");
            }
		} catch (Exception e) {
			// TODO: handle exception
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\":\"error\", \"message\":\"Lỗi hệ thống hoặc Token không hợp lệ\"}");
		}
		out.flush();
	}
}
