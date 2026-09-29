package service;

import java.util.List;

import dao.TimetablesDAO;
import entity.Timetables;

public class TimetablesService {
private TimetablesDAO timetablesDAO;
    
    public TimetablesService() {
        this.timetablesDAO = new TimetablesDAO();
    }
    
    public List<Timetables> getAllTimetables() {
        return timetablesDAO.getAllTimetables();
    }
}
