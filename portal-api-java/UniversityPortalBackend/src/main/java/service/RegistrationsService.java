package service;

import java.util.List;

import dao.RegistrationsDAO;
import entity.Registrations;

public class RegistrationsService {
private RegistrationsDAO registrationsDAO;
    
    public RegistrationsService() {
        this.registrationsDAO = new RegistrationsDAO();
    }
    
    public List<Registrations> getAllRegistrations() {
        return registrationsDAO.getAllRegistrations();
    }
}
