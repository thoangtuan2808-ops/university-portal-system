package service;

import java.util.List;

import dao.SemestersDAO;
import entity.Semesters;

public class SemestersService {
private SemestersDAO semestersDAO;
    
    public SemestersService() {
        this.semestersDAO = new SemestersDAO();
    }
    
    public List<Semesters> getAllSemesters() {
        return semestersDAO.getAllSemesters();
    }
}
