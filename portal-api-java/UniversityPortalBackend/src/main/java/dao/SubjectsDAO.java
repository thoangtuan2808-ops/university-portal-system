package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import entity.AdminClasses;
import entity.Subjects;

public class SubjectsDAO {
	public List<Subjects> getAllSubjects(){
		List<Subjects> list = new ArrayList<>();
		String sql = "SELECT SubjectID, SubjectName, Credits FROM Subjects";
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
			while(rs.next()) {
				Subjects subjects = new Subjects();
				subjects.setSubjectID(rs.getInt("SubjectID"));
				subjects.setSubjectName(rs.getString("SubjectName"));
				subjects.setCredits(rs.getInt("Credits"));
				list.add(subjects);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi truy vấn danh sách môn học: " + e.getMessage());
		}
		return list;
	}
}
