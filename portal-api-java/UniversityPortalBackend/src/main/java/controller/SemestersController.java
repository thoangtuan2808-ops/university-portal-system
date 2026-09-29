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

import entity.Semesters;
import service.SemestersService;
import service.UserService;

@WebServlet("/api/admin/semesters")
public class SemestersController extends HttpServlet {
	private SemestersService semestersService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        semestersService = new SemestersService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            out.flush(); return;
        }
        
        List<Semesters> list = semestersService.getAllSemesters();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < list.size(); i++) {
            Semesters s = list.get(i);
            
            String nameStr = s.getSemesterName() != null ? s.getSemesterName().replace("\"", "\\\"") : "";
            String startStr = s.getStartDate() != null ? sdf.format(s.getStartDate()) : "";
            String endStr = s.getEndDate() != null ? sdf.format(s.getEndDate()) : "";
            
            json.append("{");
            json.append("\"semesterId\":").append(s.getSemesterId()).append(",");
            json.append("\"semesterName\":\"").append(nameStr).append("\",");
            json.append("\"startDate\":\"").append(startStr).append("\",");
            json.append("\"endDate\":\"").append(endStr).append("\"");
            json.append("}");
            
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
    }
}
