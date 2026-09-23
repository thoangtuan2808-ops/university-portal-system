package service;

import java.util.List;

import dao.FacultiesDAO;
import entity.Faculties;

public class FacultiesService {
	private FacultiesDAO facultiesDAO;
	public FacultiesService() {
		this.facultiesDAO=new FacultiesDAO();
	}
	public List<Faculties> getAllFaculty(){
		return facultiesDAO.getAllFaculty();
	}
}
