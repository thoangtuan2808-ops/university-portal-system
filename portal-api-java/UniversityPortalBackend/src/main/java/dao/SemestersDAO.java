package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Semesters;

public class SemestersDAO {
	public List<Semesters> getAllSemesters() {
        List<Semesters> list = new ArrayList<>();
        String sql = "SELECT SemesterID, SemesterName, StartDate, EndDate FROM Semesters";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Semesters s = new Semesters();
                s.setSemesterId(rs.getInt("SemesterID"));
                s.setSemesterName(rs.getString("SemesterName"));
                s.setStartDate(rs.getDate("StartDate"));
                s.setEndDate(rs.getDate("EndDate"));
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn danh sách Học kỳ: " + e.getMessage());
        }
        return list;
    }
}
