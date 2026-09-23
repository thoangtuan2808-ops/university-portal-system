package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Curriculums;
import service.AdminClassesService;
import service.CurriculumsService;
import service.UserService;
@WebServlet("/api/admin/curriculums")
public class CurriculumsController extends HttpServlet {
	private CurriculumsService curriculumsService;
	private UserService userService;
	@Override
	public void init() throws ServletException{
		curriculumsService=new CurriculumsService();
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
        List<Curriculums> list=curriculumsService.getAllCurriculums();
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Curriculums c = list.get(i);
            
            // Xử lý null và escape ký tự ngoặc kép cho các chuỗi văn bản
            String majorNameStr = c.getMajorName() != null ? c.getMajorName().replace("\"", "\\\"") : "";
            String subjectNameStr = c.getSubjectName() != null ? c.getSubjectName().replace("\"", "\\\"") : "";
            
            json.append("{");
            json.append("\"curriculumId\":").append(c.getCurriculumID()).append(",");
            json.append("\"majorName\":\"").append(majorNameStr).append("\",");
            json.append("\"subjectName\":\"").append(subjectNameStr).append("\",");
            json.append("\"credits\":").append(c.getCredits()).append(",");
            json.append("\"expectedSemester\":").append(c.getExpectedSemester());
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
