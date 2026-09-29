package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.CourseClasses;
import service.CourseClassesService;
import service.UserService;

@WebServlet("/api/admin/course-classes")
public class CourseClassesController extends HttpServlet {
	private CourseClassesService courseClassesService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        courseClassesService = new CourseClassesService();
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
        
        List<CourseClasses> list = courseClassesService.getAllCourseClasses();
        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < list.size(); i++) {
            CourseClasses c = list.get(i);
            
            String subjectStr = c.getSubjectName() != null ? c.getSubjectName().replace("\"", "\\\"") : "";
            String semesterStr = c.getSemesterName() != null ? c.getSemesterName().replace("\"", "\\\"") : "";
            // Xử lý TeacherName có thể null do LEFT JOIN
            String teacherStr = c.getTeacherName() != null ? c.getTeacherName().replace("\"", "\\\"") : "Chưa phân công";
            
            json.append("{");
            json.append("\"courseClassId\":").append(c.getCourseClassId()).append(",");
            json.append("\"subjectName\":\"").append(subjectStr).append("\",");
            json.append("\"semesterName\":\"").append(semesterStr).append("\",");
            json.append("\"teacherName\":\"").append(teacherStr).append("\",");
            json.append("\"maxCapacity\":").append(c.getMaxCapacity());
            json.append("}");
            
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
    }
}
