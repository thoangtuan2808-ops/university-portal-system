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

import entity.StaffProfile;
import service.StaffService;
import service.UserService;

@WebServlet("/api/admin/staffs")
public class StaffProfileController extends HttpServlet {
	private StaffService staffService;
	private UserService userService;
	@Override
	public void init() throws ServletException{
		staffService = new StaffService();
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
        List<StaffProfile> list = staffService.getAllStaff();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder json = new StringBuilder("[");
        for(int i = 0; i < list.size(); i++) {
        	StaffProfile p = list.get(i);
            
            // Xử lý null để tránh lỗi NullPointerException khi gọi các phương thức
            String emailStr = p.getEmail() != null ? p.getEmail() : "";
            String dobStr = p.getDob() != null ? simpleDateFormat.format(p.getDob()) : "";
            String phoneStr = p.getPhoneNumber() != null ? p.getPhoneNumber() : "";
            String addressStr = p.getAddress() != null ? p.getAddress() : "";
            String deptStr = p.getDepartmentName() != null ? p.getDepartmentName() : "";
            String nameStr = p.getFullName() != null ? p.getFullName() : "";

            json.append("{");
            json.append("\"staffId\":").append(p.getStaffId()).append(",");
            json.append("\"userId\":").append(p.getUserId()).append(",");
            json.append("\"departmentName\":\"").append(deptStr).append("\",");
            json.append("\"fullName\":\"").append(nameStr).append("\",");
            json.append("\"email\":\"").append(emailStr).append("\",");
            json.append("\"dob\":\"").append(dobStr).append("\",");
            json.append("\"address\":\"").append(addressStr).append("\",");
            json.append("\"phoneNumber\":\"").append(phoneStr).append("\"");
            json.append("}");
            if(i < list.size() - 1) json.append(",");
        }
        json.append("]");
        out.print(json.toString());
        out.flush();
	}
}

