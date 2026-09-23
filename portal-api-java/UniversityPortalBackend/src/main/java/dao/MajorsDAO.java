package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Majors;

public class MajorsDAO {
	public List<Majors> getAllMajors(){
		List<Majors> list = new ArrayList<>();
		String sql = "SELECT \r\n"
				+ "    m.MajorID, \r\n"
				+ "    m.MajorName, \r\n"
				+ "    f.FacultyName \r\n"
				+ "FROM Majors m\r\n"
				+ "JOIN Faculties f ON m.FacultyID = f.FacultyID";
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
			while(rs.next()) {
				Majors majors = new Majors();
				majors.setMajorId(rs.getInt("MajorID"));
				majors.setMajorName(rs.getString("MajorName"));
				majors.setFacultyName(rs.getString("FacultyName"));
				list.add(majors);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi truy vấn danh sách Ngành học: " + e.getMessage());
		}
		return list;
	}
}
