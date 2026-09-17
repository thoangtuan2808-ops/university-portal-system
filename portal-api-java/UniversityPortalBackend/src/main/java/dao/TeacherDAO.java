package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.TeacherProfile;

public class TeacherDAO {
	public List<TeacherProfile> getAllTeachers(){
		List<TeacherProfile> list = new ArrayList<>();
		String sql ="SELECT t.TeacherID, t.UserID, t.FullName, u.Email, t.DOB, t.PhoneNumber, t.Degree, t.Address, f.FacultyName\r\n"
				+ "FROM Teachers t\r\n"
				+ "JOIN Users u ON t.UserID = u.UserID\r\n"
				+ "JOIN Faculties f ON t.FacultyID = f.FacultyID";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					TeacherProfile profile = new TeacherProfile();
					profile.setTeacherId(rs.getInt("TeacherID"));
					profile.setUserId(rs.getInt("UserID"));
					profile.setFullName(rs.getString("FullName"));
					profile.setEmail(rs.getString("Email"));
					profile.setDob(rs.getDate("DOB"));
					profile.setPhoneNumber(rs.getString("PhoneNumber"));
					profile.setDegree(rs.getString("Degree"));
					profile.setAddress(rs.getString("Address"));
					profile.setFacultyName(rs.getString("FacultyName"));
					list.add(profile);
				}
		} catch (Exception e) {
			// TODO: handle exception
			 System.out.println("Lỗi truy vấn danh sách giảng viên: " + e.getMessage());
		}
		return list;
	}
}
