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

import entity.StudentProfile;
import entity.TeacherProfile;
import service.StudentService;
import service.TeacherService;
import service.UserService;

@WebServlet("/api/admin/teachers")
public class TeacherProfileController extends HttpServlet {
	private TeacherService teacherService;
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
        teacherService = new TeacherService();
        userService = new UserService();
    }
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Cấu hình trả về chuẩn JSON có dấu tiếng Việt
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // 1. KIỂM TRA QUYỀN ADMIN (ROLE 1 VÀ 4)
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
            out.flush(); return;
        }
     // 2. LẤY DANH SÁCH VÀ ÉP KIỂU JSON ARRAY
        List<TeacherProfile> list = teacherService.getAllTeachers();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder json = new StringBuilder("[");
        
        for(int i = 0; i < list.size(); i++) {
            TeacherProfile p = list.get(i);
            String emailStr = p.getEmail() != null ? p.getEmail() : "";
            String dobStr = p.getDob() != null ? sdf.format(p.getDob()) : "";
            String phoneStr = p.getPhoneNumber() != null ? p.getPhoneNumber() : "";
            String degreeStr = p.getDegree() != null ? p.getDegree() : "";
            String addressStr = p.getAddress() != null ? p.getAddress() : "";
            String facultyStr = p.getFacultyName() != null ? p.getFacultyName() : "";
            json.append("{");
            json.append("\"teacherId\":").append(p.getTeacherId()).append(",");
            json.append("\"userId\":").append(p.getUserId()).append(",");
            json.append("\"fullName\":\"").append(p.getFullName()).append("\",");
            json.append("\"email\":\"").append(emailStr).append("\",");
            json.append("\"dob\":\"").append(dobStr).append("\",");
            json.append("\"phoneNumber\":\"").append(phoneStr).append("\",");
            json.append("\"degree\":\"").append(degreeStr).append("\",");
            json.append("\"address\":\"").append(addressStr).append("\",");
            json.append("\"facultyName\":\"").append(facultyStr).append("\"");
            json.append("}");
            if(i < list.size() - 1) json.append(",");
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
	}
}
