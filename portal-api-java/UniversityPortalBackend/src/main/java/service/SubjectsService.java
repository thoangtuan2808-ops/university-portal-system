package service;

import java.util.List;

import dao.SubjectsDAO;
import entity.Subjects;

public class SubjectsService {
	private SubjectsDAO subjectsDAO;
	public SubjectsService() {
		this.subjectsDAO = new SubjectsDAO();
	}
	public List<Subjects> getAllSubjects(){
		return subjectsDAO.getAllSubjects();
	}
}
