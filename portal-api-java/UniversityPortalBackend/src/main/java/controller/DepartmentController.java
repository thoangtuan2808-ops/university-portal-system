package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Department;
import service.DepartmentService;
import service.StaffService;
import service.UserService;

@WebServlet("/api/admin/departments")
public class DepartmentController extends HttpServlet {
	private DepartmentService departmentService;
	private UserService userService;
	@Override
	public void init() throws ServletException{
		departmentService = new DepartmentService();
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
        List<Department> list = departmentService.getAllDepartment();
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Department d = list.get(i);
            
            // Xử lý null cho tên phòng ban để tránh lỗi JSON
            String deptName = d.getDepartmentName() != null ? d.getDepartmentName() : "";

            json.append("{");
            json.append("\"departmentId\":").append(d.getDepartmentId()).append(",");
            json.append("\"departmentName\":\"").append(deptName).append("\"");
            json.append("}");
            
            // Thêm dấu phẩy giữa các object (trừ object cuối cùng)
            if (i < list.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
     }
}
