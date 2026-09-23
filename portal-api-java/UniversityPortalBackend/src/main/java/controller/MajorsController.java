package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Majors;
import service.MajorsService;
import service.StaffService;
import service.UserService;
@WebServlet("/api/admin/majors")
public class MajorsController extends HttpServlet {
	private MajorsService majorsService;
	private UserService userService;
	@Override
	public void init() throws ServletException{
		majorsService = new MajorsService();
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
        List<Majors> list = majorsService.getAllMajors();
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Majors m = list.get(i);
            
            // Xử lý null và ký tự ngoặc kép
            String majorNameStr = m.getMajorName() != null ? m.getMajorName().replace("\"", "\\\"") : "";
            String facultyNameStr = m.getFacultyName() != null ? m.getFacultyName().replace("\"", "\\\"") : "";
            
            json.append("{");
            json.append("\"majorId\":").append(m.getMajorId()).append(",");
            json.append("\"majorName\":\"").append(majorNameStr).append("\",");
            json.append("\"facultyName\":\"").append(facultyNameStr).append("\"");
            json.append("}");
            
            if (i < list.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
     }
	
}
