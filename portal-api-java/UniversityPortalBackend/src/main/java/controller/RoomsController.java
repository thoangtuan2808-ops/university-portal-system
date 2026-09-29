package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Rooms;
import service.RoomsService;
import service.UserService;

@WebServlet("/api/admin/rooms")
public class RoomsController extends HttpServlet {
	private RoomsService roomsService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        roomsService = new RoomsService();
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
        
        List<Rooms> list = roomsService.getAllRooms();
        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < list.size(); i++) {
            Rooms r = list.get(i);
            
            String nameStr = r.getRoomName() != null ? r.getRoomName().replace("\"", "\\\"") : "";
            String typeStr = r.getRoomType() != null ? r.getRoomType().replace("\"", "\\\"") : "";
            
            json.append("{");
            json.append("\"roomId\":").append(r.getRoomId()).append(",");
            json.append("\"roomName\":\"").append(nameStr).append("\",");
            json.append("\"capacity\":").append(r.getCapacity()).append(",");
            json.append("\"roomType\":\"").append(typeStr).append("\"");
            json.append("}");
            
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
    }
}
