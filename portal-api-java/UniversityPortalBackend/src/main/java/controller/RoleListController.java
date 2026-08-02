package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Role;
import service.RoleService;

@WebServlet("/api/roles")
public class RoleListController extends HttpServlet {
	private RoleService roleService;
	    
	public void init() { roleService = new RoleService(); }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    List<Role> roles = roleService.getAllRoles();
	    StringBuilder json = new StringBuilder("[");
	    
	    for (int i = 0; i < roles.size(); i++) {
	        Role r = roles.get(i);
	        json.append("{\"roleId\":").append(r.getRoleId())
	            .append(",\"roleName\":\"").append(r.getRoleName()).append("\"}");
	        if (i < roles.size() - 1) json.append(",");
	    }
	    json.append("]");
	    
	    out.print(json.toString());
	    out.flush();
	    }
}
