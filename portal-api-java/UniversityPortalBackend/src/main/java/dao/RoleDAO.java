package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Role;

public class RoleDAO {
	public List<Role> getAllRoles(){
		List<Role> list=new ArrayList<>();
		String sql = "SELECT RoleID, RoleName FROM Roles";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				list.add(new Role(rs.getInt("RoleID"), rs.getString("RoleName")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi lấy Role: "+e.getMessage());
		}
		return list;
	}
}
