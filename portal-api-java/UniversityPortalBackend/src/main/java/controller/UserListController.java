package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;

@WebServlet("/api/users")
public class UserListController extends HttpServlet {
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		userService = new UserService();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		List<User> list = userService.getAllUsers();
		StringBuilder json = new StringBuilder();
		json.append("[");
		for(int i = 0; i<list.size(); i++) {
			User u = list.get(i);
			json.append("{");
			json.append("\"userId\":").append(u.getUserId()).append(",");
			json.append("\"userName\":\"").append(u.getUserName()).append("\",");
			json.append("\"email\":\"").append(u.getEmail()!= null ? u.getEmail():"").append("\",");
			json.append("\"roleId\":").append(u.getRoleId()).append(",");
			json.append("\"isActive\":").append(u.isActive()).append(",");
			json.append("\"createAt\":\"").append(u.getCreatedAt()).append("\"");
			json.append("}");
			if(i<list.size()-1) {
				json.append(",");
			}
		}
		json.append("]");
		out.print(json.toString());
		out.flush();
	}
}
