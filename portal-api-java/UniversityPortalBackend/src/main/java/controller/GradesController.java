package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Grades;
import service.GradesService;
import service.UserService;

@WebServlet("/api/admin/grades")
public class GradesController extends HttpServlet {
    private GradesService gradesService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        gradesService = new GradesService();
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
        
        List<Grades> list = gradesService.getAllGrades();
        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < list.size(); i++) {
            Grades g = list.get(i);
            
            String studentStr = g.getStudentName() != null ? g.getStudentName().replace("\"", "\\\"") : "";
            String subjectStr = g.getSubjectName() != null ? g.getSubjectName().replace("\"", "\\\"") : "";
            
            json.append("{");
            json.append("\"gradeId\":").append(g.getGradeId()).append(",");
            json.append("\"studentName\":\"").append(studentStr).append("\",");
            json.append("\"subjectName\":\"").append(subjectStr).append("\",");
            json.append("\"midtermScore\":").append(g.getMidtermScore()).append(",");
            json.append("\"finalScore\":").append(g.getFinalScore());
            json.append("}");
            
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
    }
}
