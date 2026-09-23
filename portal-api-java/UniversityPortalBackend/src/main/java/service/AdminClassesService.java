package service;

import java.util.List;

import dao.AdminClassesDAO;
import entity.AdminClasses;

public class AdminClassesService {
	private AdminClassesDAO adminClassesDAO;
	public AdminClassesService() {
		this.adminClassesDAO = new AdminClassesDAO();
	}
	public List<AdminClasses> getAllAdminClasses(){
		return adminClassesDAO.getAllAdminClasses();
	}
}
