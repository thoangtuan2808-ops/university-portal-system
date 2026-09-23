package service;

import java.util.List;

import dao.CurriculumsDAO;
import entity.Curriculums;

public class CurriculumsService {
	private CurriculumsDAO curriculumsDAO;
	public CurriculumsService() {
		this.curriculumsDAO=new CurriculumsDAO();
	}
	public List<Curriculums> getAllCurriculums(){
		return curriculumsDAO.getAllCurriculums();
	}
}
