package service;

import java.util.List;

import dao.GradesDAO;
import entity.Grades;

public class GradesService {
private GradesDAO gradesDAO;
    
    public GradesService() {
        this.gradesDAO = new GradesDAO();
    }
    
    public List<Grades> getAllGrades() {
        return gradesDAO.getAllGrades();
    }
}
