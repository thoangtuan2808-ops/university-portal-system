package service;

import java.util.List;

import dao.DepartmentDAO;
import entity.Department;

public class DepartmentService {
	private DepartmentDAO departmentDAO;
	public DepartmentService() {
		this.departmentDAO=new DepartmentDAO();
	}
	public List<Department> getAllDepartment(){
		return departmentDAO.getAllDepartment();
	}
}
