package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.StaffProfile;

public class StaffDAO {
	public List<StaffProfile> getAllStaff(){
		List<StaffProfile> list = new ArrayList<>();
		String sql = "SELECT s.StaffID, s.UserID, d.DepartmentName, s.FullName, u.Email, s.DOB, s.Address, s.PhoneNumber\r\n"
				+ "FROM Staffs s\r\n"
				+ "JOIN Users u ON s.UserID = u.UserID\r\n"
				+ "JOIN Departments d ON s.DepartmentID = d.DepartmentID;" ;
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
			while(rs.next()) {
				StaffProfile profile = new StaffProfile();
				profile.setStaffId(rs.getInt("StaffID"));
				profile.setUserId(rs.getInt("UserID"));
				profile.setDepartmentName(rs.getString("DepartmentName"));
				profile.setFullName(rs.getString("FullName"));
				profile.setEmail(rs.getString("Email"));
				profile.setDob(rs.getDate("DOB"));
				profile.setAddress(rs.getString("Address"));
				profile.setPhoneNumber(rs.getString("PhoneNumber"));
				list.add(profile);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi truy vấn danh sách nhân viên: " + e.getMessage());
		}
		return list;
	}
	
}
