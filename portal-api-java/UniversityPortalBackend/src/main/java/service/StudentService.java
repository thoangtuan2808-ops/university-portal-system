package service;

import java.util.List;

import dao.StudentDAO;
import entity.StudentProfile;

public class StudentService {
	private StudentDAO studentDAO;
	public StudentService() {
		this.studentDAO=new StudentDAO();
	}
	public StudentProfile getStudentProfile(int userId) {
		return studentDAO.getStudentProfileByUserId(userId);
	}
	public List<StudentProfile> getAllStudents() {
		return studentDAO.getAllStudents();
	}
}
