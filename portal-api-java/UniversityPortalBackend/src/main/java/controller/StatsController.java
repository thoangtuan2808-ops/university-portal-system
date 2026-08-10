package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.StatsManager;
@WebServlet("/api/stats")
public class StatsController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // Trả về chuỗi JSON chứa các con số hiện tại
        String json = String.format("{\"total\": %d, \"students\": %d, \"teachers\": %d}", 
                StatsManager.getTotal(), 
                StatsManager.getActiveStudents(), 
                StatsManager.getActiveTeachers());
                
        out.print(json);
        out.flush();
    }
}
