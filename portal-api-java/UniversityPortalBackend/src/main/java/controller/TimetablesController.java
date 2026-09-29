package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Timetables;
import service.TimetablesService;
import service.UserService;

@WebServlet("/api/admin/timetables")
public class TimetablesController extends HttpServlet {
	private TimetablesService timetablesService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        timetablesService = new TimetablesService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // Kiểm tra Token JWT
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
        
        List<Timetables> list = timetablesService.getAllTimetables();
        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < list.size(); i++) {
            Timetables t = list.get(i);
            
            // Ép chuỗi an toàn, tránh lỗi vỡ JSON do ngoặc kép
            String subjectStr = t.getSubjectName() != null ? t.getSubjectName().replace("\"", "\\\"") : "";
            String roomStr = t.getRoomName() != null ? t.getRoomName().replace("\"", "\\\"") : "";
            String dayStr = t.getDayOfWeek() != null ? t.getDayOfWeek().replace("\"", "\\\"") : "";
            
            json.append("{");
            json.append("\"scheduleId\":").append(t.getScheduleId()).append(",");
            json.append("\"subjectName\":\"").append(subjectStr).append("\",");
            json.append("\"roomName\":\"").append(roomStr).append("\",");
            json.append("\"dayOfWeek\":\"").append(dayStr).append("\",");
            json.append("\"shift\":").append(t.getShift());
            json.append("}");
            
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
    }
}
