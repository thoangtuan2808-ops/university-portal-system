package service;

import java.util.List;

import dao.MajorsDAO;
import entity.Majors;

public class MajorsService {
	private MajorsDAO majorsDAO;
	public MajorsService() {
		this.majorsDAO = new MajorsDAO();
	}
	public List<Majors> getAllMajors() {
		return majorsDAO.getAllMajors();
	}
}
