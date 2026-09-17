package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.StudentProfile;

public class StudentDAO {
	public List<StudentProfile> getAllStudents(){
		List<StudentProfile> list = new ArrayList<>();
		String sql = "SELECT s.StudentID, s.UserID, s.FullName, s.DOB, s.Address, "
                + "u.Email, c.ClassName, m.MajorName, f.FacultyName "
                + "FROM Students s "
                + "INNER JOIN Users u ON s.UserID = u.UserID "
                + "INNER JOIN AdminClasses c ON s.ClassID = c.ClassID "
                + "INNER JOIN Majors m ON c.MajorID = m.MajorID "
                + "INNER JOIN Faculties f ON m.FacultyID = f.FacultyID "
                + "ORDER BY s.StudentID DESC"; // Sắp xếp sinh viên mới nhất lên đầu
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	             
	            while (rs.next()) {
	                StudentProfile profile = new StudentProfile();
	                profile.setStudentId(rs.getInt("StudentID"));
	                profile.setUserId(rs.getInt("UserID"));
	                profile.setFullName(rs.getString("FullName"));
	                profile.setDob(rs.getDate("DOB"));
	                profile.setAddress(rs.getString("Address"));
	                profile.setEmail(rs.getString("Email"));
	                profile.setClassName(rs.getString("ClassName"));
	                profile.setMajorName(rs.getString("MajorName"));
	                profile.setFacultyName(rs.getString("FacultyName"));
	                
	                list.add(profile); // Nhét từng người vào danh sách
	            }
	        } catch (Exception e) {
	            System.out.println("Lỗi truy vấn danh sách sinh viên: " + e.getMessage());
	        }
		return list;
	}
	public StudentProfile getStudentProfileByUserId (int userId) {
		StudentProfile profile = null;
		String sql = "SELECT s.StudentID, s.UserID, s.FullName, s.DOB, s.Address, "
                + "u.Email, "
                + "c.ClassName, "
                + "m.MajorName, "
                + "f.FacultyName "
                + "FROM Students s "
                + "INNER JOIN Users u ON s.UserID = u.UserID "
                + "INNER JOIN AdminClasses c ON s.ClassID = c.ClassID "
                + "INNER JOIN Majors m ON c.MajorID = m.MajorID "
                + "INNER JOIN Faculties f ON m.FacultyID = f.FacultyID "
                + "WHERE s.UserID = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					profile = new StudentProfile();
                    profile.setStudentId(rs.getInt("StudentID"));
                    profile.setUserId(rs.getInt("UserID"));
                    profile.setFullName(rs.getString("FullName"));
                    profile.setDob(rs.getDate("DOB"));
                    profile.setAddress(rs.getString("Address"));
                    profile.setEmail(rs.getString("Email"));
                    profile.setClassName(rs.getString("ClassName"));
                    profile.setMajorName(rs.getString("MajorName"));
                    profile.setFacultyName(rs.getString("FacultyName"));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi truy vấn hồ sơ sinh viên: "+e.getMessage());
			e.printStackTrace();
		}
		return profile;
	}
}
