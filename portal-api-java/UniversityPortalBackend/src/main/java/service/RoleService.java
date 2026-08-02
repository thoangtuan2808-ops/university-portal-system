package service;

import java.util.List;

import dao.RoleDAO;
import entity.Role;

public class RoleService {
	private RoleDAO roleDAO = new RoleDAO();
	public RoleService() {
		this.roleDAO=new RoleDAO();
	}
	public List<Role> getAllRoles(){
		return roleDAO.getAllRoles();
	}
}
