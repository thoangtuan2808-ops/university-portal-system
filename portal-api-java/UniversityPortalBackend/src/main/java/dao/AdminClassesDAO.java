package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.AdminClasses;

public class AdminClassesDAO {
	public List<AdminClasses> getAllAdminClasses(){
		List<AdminClasses> list = new ArrayList<>();
		String sql = "SELECT \r\n"
				+ "    c.ClassID, \r\n"
				+ "    c.ClassName, \r\n"
				+ "    m.MajorName \r\n"
				+ "FROM AdminClasses c\r\n"
				+ "JOIN Majors m ON c.MajorID = m.MajorID;";
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
			while(rs.next()) {
				AdminClasses adminClasses = new AdminClasses();
				adminClasses.setClassId(rs.getInt("ClassID"));
				adminClasses.setClassName(rs.getString("ClassName"));
				adminClasses.setMajorName(rs.getString("MajorName"));
				list.add(adminClasses);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi truy vấn danh sách lớp hành chính: " + e.getMessage());
		}
		return list;
	}
}
