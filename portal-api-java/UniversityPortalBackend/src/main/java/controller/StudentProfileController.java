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
import service.StudentService;
import service.UserService;

@WebServlet("/api/admin/students")
public class StudentProfileController extends HttpServlet{
	private StudentService studentService;
	private UserService userService;// Gọi UserService để xài lại hàm checkAuthorization
	
	@Override
	public void init() throws ServletException {
        studentService = new StudentService();
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
        List<StudentProfile> list = studentService.getAllStudents();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder json = new StringBuilder("[");
        
        for(int i = 0; i < list.size(); i++) {
            StudentProfile p = list.get(i);
            String dobStr = p.getDob() != null ? sdf.format(p.getDob()) : "";
            String addressStr = p.getAddress() != null ? p.getAddress() : "";
            String emailStr = p.getEmail() != null ? p.getEmail() : "";
            String classStr = p.getClassName() != null ? p.getClassName() : "";
            String majorStr = p.getMajorName() != null ? p.getMajorName() : "";
            String facultyStr = p.getFacultyName() != null ? p.getFacultyName() : "";
            json.append("{");
            json.append("\"studentId\":").append(p.getStudentId()).append(",");
            json.append("\"userId\":").append(p.getUserId()).append(",");
            json.append("\"fullName\":\"").append(p.getFullName()).append("\",");
            json.append("\"dob\":\"").append(dobStr).append("\",");
            json.append("\"address\":\"").append(addressStr).append("\",");
            json.append("\"email\":\"").append(emailStr).append("\",");
            json.append("\"className\":\"").append(classStr).append("\",");
            json.append("\"majorName\":\"").append(majorStr).append("\",");
            json.append("\"facultyName\":\"").append(facultyStr).append("\"");
            json.append("}");
            if(i < list.size() - 1) json.append(",");
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
	}
}
