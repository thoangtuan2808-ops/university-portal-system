package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.AdminClasses;
import service.AdminClassesService;
import service.DepartmentService;
import service.UserService;
@WebServlet("/api/admin/adminclass")
public class AdminClassesController extends HttpServlet {
	private AdminClassesService adminClassesService;
	private UserService userService;
	@Override
	public void init() throws ServletException{
		adminClassesService = new AdminClassesService();
		userService = new UserService();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            out.print("{\"status\":\"error\",\"message\":\"Thiếu thẻ bài\"}");
            out.flush(); return;
        }
        String token = authHeader.substring(7);
        if (!userService.checkAuthorization(token, 4, 1)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
            out.print("{\"status\":\"error\",\"message\":\"Chỉ QTV mới được xem danh sách này\"}");
            out.flush(); 
            return;
        }
        List<AdminClasses> list = adminClassesService.getAllAdminClasses();
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            AdminClasses c = list.get(i);
            
            // Xử lý null và escape ký tự ngoặc kép để tránh vỡ cấu trúc JSON
            String classNameStr = c.getClassName() != null ? c.getClassName().replace("\"", "\\\"") : "";
            String majorNameStr = c.getMajorName() != null ? c.getMajorName().replace("\"", "\\\"") : "";
            
            json.append("{");
            json.append("\"classId\":").append(c.getClassId()).append(",");
            json.append("\"className\":\"").append(classNameStr).append("\",");
            json.append("\"majorName\":\"").append(majorNameStr).append("\"");
            json.append("}");
            
            // Thêm dấu phẩy phân cách giữa các object
            if (i < list.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
	}
}
