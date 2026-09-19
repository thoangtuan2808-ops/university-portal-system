package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Department;

public class DepartmentDAO {
	public List<Department> getAllDepartment(){
		List<Department> list = new ArrayList<>();
		String sql = "Select DepartmentID, DepartmentName from Departments";
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()){
			while(rs.next()){
				Department department= new Department();
				department.setDepartmentId(rs.getInt("DepartmentID"));
				department.setDepartmentName(rs.getString("DepartmentName"));
				list.add(department);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi truy vấn danh sách phòng ban: " + e.getMessage());
		}
		return list;
	}
}
