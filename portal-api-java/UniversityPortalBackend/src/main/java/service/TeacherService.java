package service;

import java.util.List;

import dao.TeacherDAO;
import entity.TeacherProfile;

public class TeacherService {
	private TeacherDAO teacherDAO;
	public TeacherService() {
		this.teacherDAO=new TeacherDAO();
	}
	public List<TeacherProfile> getAllTeachers(){
		return teacherDAO.getAllTeachers();
	}
}
