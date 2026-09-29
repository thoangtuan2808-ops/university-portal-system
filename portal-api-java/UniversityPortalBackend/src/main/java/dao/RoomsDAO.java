package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Rooms;

public class RoomsDAO {
	public List<Rooms> getAllRooms() {
        List<Rooms> list = new ArrayList<>();
        String sql = "SELECT RoomID, RoomName, Capacity, RoomType FROM Rooms";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Rooms r = new Rooms();
                r.setRoomId(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setCapacity(rs.getInt("Capacity"));
                r.setRoomType(rs.getString("RoomType"));
                list.add(r);
            }
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn danh sách Phòng học: " + e.getMessage());
        }
        return list;
    }
}
