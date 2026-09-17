package service;

import java.util.List;

import dao.StaffDAO;
import entity.StaffProfile;

public class StaffService {
	private StaffDAO staffDAO;
	public StaffService() {
		this.staffDAO=new StaffDAO();
	}
	public List<StaffProfile> getAllStaff(){
		return staffDAO.getAllStaff();
	}
}
