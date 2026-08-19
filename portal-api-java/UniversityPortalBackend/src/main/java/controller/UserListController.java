package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;

@WebServlet("/api/users")
public class UserListController extends HttpServlet {
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		userService = new UserService();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//Thêm ngày 18/08/2026: CHỐT CHẶN BẢO MẬT JWT 
		String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Lỗi 401
            out.print("{\"status\":\"error\",\"message\":\"Thiếu hoặc sai Token bảo mật\"}");
            out.flush();
            return;
        }
        String token = authHeader.substring(7);
        // Chỉ cho phép Super Admin (4) và Admin (1) gọi API này
        if (!userService.checkAuthorization(token, 4, 1)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Lỗi 403
            out.print("{\"status\":\"error\",\"message\":\"Bạn không có quyền xem danh sách này\"}");
            out.flush();
            return;
        }
        // --- 2. LỌC DANH SÁCH THEO ROLE (TỐI ƯU BĂNG THÔNG) ---
        String roleParam = request.getHeader("X-Role-Id"); 
        
        if (roleParam == null || roleParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
            out.print("{\"status\":\"error\",\"message\":\"Không tìm thấy thẻ bài phân luồng (X-Role-Id) trong Header!\"}");
            out.flush();
            return;
        }
        // Ép kiểu và gọi DAO
        int roleId = Integer.parseInt(roleParam);
        List<User> list = userService.getUsersByRole(roleId);
        
        // --- 3. ĐÓNG GÓI JSON VÀ LÀM ĐẸP NGÀY THÁNG ---
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder json = new StringBuilder("[");
        
        for(int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            String formattedDate = u.getCreatedAt() != null ? sdf.format(u.getCreatedAt()) : "";
            
            json.append("{");
            json.append("\"userId\":").append(u.getUserId()).append(",");
            json.append("\"userName\":\"").append(u.getUserName()).append("\",");
            json.append("\"email\":\"").append(u.getEmail() != null ? u.getEmail() : "").append("\",");
            json.append("\"roleId\":").append(u.getRoleId()).append(",");
            json.append("\"isActive\":").append(u.isActive()).append(",");
            json.append("\"createAt\":\"").append(formattedDate).append("\"");
            json.append("}");
            if(i < list.size() - 1) {
                json.append(",");
            }
        }
        //------------------------------------------
		json.append("]");
		out.print(json.toString());
		out.flush();
	}
}
