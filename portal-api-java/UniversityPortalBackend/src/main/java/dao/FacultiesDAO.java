package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Faculties;

public class FacultiesDAO {
	public List<Faculties> getAllFaculty(){
		List<Faculties> list = new ArrayList<>();
		String sql = "SELECT FacultyID, FacultyName FROM Faculties";
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
			while(rs.next()) {
				Faculties faculties = new Faculties();
				faculties.setFacultyId(rs.getInt("FacultyID"));
				faculties.setFacultyName(rs.getString("FacultyName"));
				list.add(faculties);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi truy vấn danh sách Khoa: " + e.getMessage());
		}
		return list;
	}
}
