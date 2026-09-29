package service;

import java.util.List;

import dao.RoomsDAO;
import entity.Rooms;

public class RoomsService {
private RoomsDAO roomsDAO;
    
    public RoomsService() {
        this.roomsDAO = new RoomsDAO();
    }
    
    public List<Rooms> getAllRooms() {
        return roomsDAO.getAllRooms();
    }
}
