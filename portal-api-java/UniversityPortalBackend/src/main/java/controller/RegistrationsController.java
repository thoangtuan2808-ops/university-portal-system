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

import entity.Registrations;
import service.RegistrationsService;
import service.UserService;

@WebServlet("/api/admin/registrations")
public class RegistrationsController extends HttpServlet {
    private RegistrationsService registrationsService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        registrationsService = new RegistrationsService();
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
        
        List<Registrations> list = registrationsService.getAllRegistrations();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < list.size(); i++) {
            Registrations r = list.get(i);
            
            String studentStr = r.getStudentName() != null ? r.getStudentName().replace("\"", "\\\"") : "";
            String subjectStr = r.getSubjectName() != null ? r.getSubjectName().replace("\"", "\\\"") : "";
            String dateStr = r.getRegistrationDate() != null ? sdf.format(r.getRegistrationDate()) : "";
            String statusStr = r.getStatus() != null ? r.getStatus().replace("\"", "\\\"") : "";
            
            json.append("{");
            json.append("\"registrationId\":").append(r.getRegistrationId()).append(",");
            json.append("\"studentName\":\"").append(studentStr).append("\",");
            json.append("\"subjectName\":\"").append(subjectStr).append("\",");
            json.append("\"registrationDate\":\"").append(dateStr).append("\",");
            json.append("\"status\":\"").append(statusStr).append("\"");
            json.append("}");
            
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
    }
}
