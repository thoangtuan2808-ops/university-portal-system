package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;
@WebServlet("/api/users/lock")
public class UserLockController extends HttpServlet {
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		userService = new UserService();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
			boolean success = userService.lockUser(userId);
			if(success) {
				out.print("{\"status\":\"success\",\"message\":\"Đã khóa tài khoản\"}");
			}
			else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.print("{\"\status\":\"error\", \"message\":\"Không tìm thấy tài khoản\"}");
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("{\"\status\":\"error\", \"message\":\"Lỗi hệ thống\"}");
		}
		out.flush();
	}
}
