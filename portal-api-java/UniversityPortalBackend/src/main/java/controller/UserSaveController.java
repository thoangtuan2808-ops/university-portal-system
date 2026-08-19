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
@WebServlet("/api/users/save")
public class UserSaveController extends HttpServlet {
	private UserService userService;
	
	@Override
	public void init() throws ServletException{
		userService = new UserService();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String authHeader = request.getHeader("Authorization");
		int tokenRoleId = -1; // Biến lưu giữ Role của người đang thao tác
		
		try {
		    // Kiểm tra thẻ có tồn tại không
		    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
		        throw new Exception("Thiếu Token bảo mật!");
		    }
		    
		    // Cắt lấy lõi Token và soi chữ ký
		    String token = authHeader.substring(7);
		    Algorithm algorithm = Algorithm.HMAC256("ChuoiKhoaBiMatCuaRiengBan_KhongDuocDeLo");
		    JWTVerifier verifier = JWT.require(algorithm).withIssuer("UniversityPortal").build();
		    DecodedJWT decodedJWT = verifier.verify(token);
		    
		    //Thêm ngày 18/08/2026: Phân quyền truy cập
		    tokenRoleId = decodedJWT.getClaim("roleId").asInt();
            if (tokenRoleId != 4 && tokenRoleId != 1) { // Chỉ Super Admin(4) và Admin(1) được vào
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
                out.print("{\"status\":\"error\", \"message\":\"Bạn không có quyền quản trị!\"}");
                out.flush();
                return; 
            }
            
		} catch (Exception ex) {
		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Mã 401
		    out.print("{\"status\":\"error\", \"message\":\"Xác thực thất bại!\"}");
		    out.flush();
		    return; // ĐÁ VĂNG
		}
		try {
			//nhận dữ liệu từ c#
			int userID=Integer.parseInt(request.getParameter("userId"));
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			int targetRoleId = Integer.parseInt(request.getParameter("roleId"));
			boolean isActive = Integer.parseInt(request.getParameter("isActive"))==1;
			
			//Nếu người đang thao tác là Admin thường (1), KHÔNG cho phép gán quyền 4 hoặc 1 cho tài khoản khác
			if (tokenRoleId == 1 && (targetRoleId == 4 || targetRoleId == 1)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print("{\"status\":\"error\", \"message\":\"Admin không được phép tạo/sửa tài khoản ngang cấp hoặc cao hơn!\"}");
                out.flush();
                return;
            }
			//đóng gói dữ liệu
			User u = new User();
			u.setUserId(userID);
			u.setUserName(username);
			u.setPasswordHash(password);
			u.setEmail(email);
			u.setRoleId(targetRoleId);
			u.setActive(isActive);
			boolean success=false;
			String act="";
			if(userID==0) {
				success=userService.addUser(u);
				act="Thêm";
			}else {
				success=userService.updateUser(u);
				act="Cập nhật";
			}
			if(success) {
				out.print("{\"status\":\"success\", \"message\":\"" + act + " tài khoản thành công!\"}");
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"status\":\"error\", \"message\":\"Lỗi không thể lưu vào cơ sở dữ liệu.\"}");
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\":\"error\", \"message\":\"Lỗi dữ liệu gửi lên không hợp lệ.\"}");
		}
		out.flush();
	}
}
