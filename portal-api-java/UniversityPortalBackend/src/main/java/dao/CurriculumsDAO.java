package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Curriculums;

public class CurriculumsDAO {
	public List<Curriculums> getAllCurriculums(){
		List<Curriculums> list = new ArrayList<>();
		String sql = "SELECT \r\n"
				+ "    c.CurriculumID, \r\n"
				+ "    m.MajorName, \r\n"
				+ "    s.SubjectName, \r\n"
				+ "    s.Credits, \r\n"
				+ "    c.ExpectedSemester \r\n"
				+ "FROM Curriculums c\r\n"
				+ "JOIN Majors m ON c.MajorID = m.MajorID\r\n"
				+ "JOIN Subjects s ON c.SubjectID = s.SubjectID";
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
			while(rs.next()) {
				Curriculums curriculums = new Curriculums();
				curriculums.setCurriculumID(rs.getInt("CurriculumID"));
				curriculums.setMajorName(rs.getString("MajorName"));
				curriculums.setSubjectName(rs.getString("SubjectName"));
				curriculums.setCredits(rs.getInt("Credits"));
				curriculums.setExpectedSemester(rs.getInt("ExpectedSemester"));
				list.add(curriculums);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi truy vấn chương trình đào tạo: " + e.getMessage());
		}
		return list;
	}
}
