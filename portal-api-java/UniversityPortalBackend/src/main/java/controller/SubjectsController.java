package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Faculties;
import entity.Subjects;
import service.FacultiesService;
import service.SubjectsService;
import service.UserService;
@WebServlet("/api/admin/subjects")
public class SubjectsController extends HttpServlet {
	private SubjectsService subjectsService;
	private UserService userService;
	@Override
	public void init() throws ServletException{
		subjectsService = new SubjectsService();
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
        List<Subjects> list = subjectsService.getAllSubjects();
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Subjects s = list.get(i);
            
            // Xử lý null và escape ký tự ngoặc kép để cấu trúc JSON không bị lỗi
            String subjectNameStr = s.getSubjectName() != null ? s.getSubjectName().replace("\"", "\\\"") : "";
            
            json.append("{");
            json.append("\"subjectId\":").append(s.getSubjectID()).append(",");
            json.append("\"subjectName\":\"").append(subjectNameStr).append("\",");
            json.append("\"credits\":").append(s.getCredits());
            json.append("}");
            
            // Thêm dấu phẩy phân cách giữa các đối tượng trong mảng
            if (i < list.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
	}
}
