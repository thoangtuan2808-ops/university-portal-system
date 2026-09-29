package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Timetables;

public class TimetablesDAO {
	public List<Timetables> getAllTimetables() {
        List<Timetables> list = new ArrayList<>();
        String sql = "SELECT t.ScheduleID, s.SubjectName, r.RoomName, t.DayOfWeek, t.Shift "
                   + "FROM Timetables t "
                   + "JOIN CourseClasses cc ON t.CourseClassID = cc.CourseClassID "
                   + "JOIN Subjects s ON cc.SubjectID = s.SubjectID "
                   + "JOIN Rooms r ON t.RoomID = r.RoomID";
                   
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Timetables t = new Timetables();
                t.setScheduleId(rs.getInt("ScheduleID"));
                t.setSubjectName(rs.getString("SubjectName"));
                t.setRoomName(rs.getString("RoomName"));
                t.setDayOfWeek(rs.getString("DayOfWeek"));
                t.setShift(rs.getInt("Shift"));
                list.add(t);
            }
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn Thời khóa biểu: " + e.getMessage());
        }
        return list;
    }
}
