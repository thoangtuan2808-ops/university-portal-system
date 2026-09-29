package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Registrations;

public class RegistrationsDAO {
	public List<Registrations> getAllRegistrations() {
        List<Registrations> list = new ArrayList<>();
        String sql = "SELECT r.RegistrationID, stu.FullName AS StudentName, sub.SubjectName, r.RegistrationDate, r.Status "
                   + "FROM Registrations r "
                   + "JOIN Students stu ON r.StudentID = stu.StudentID "
                   + "JOIN CourseClasses cc ON r.CourseClassID = cc.CourseClassID "
                   + "JOIN Subjects sub ON cc.SubjectID = sub.SubjectID";
                   
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Registrations r = new Registrations();
                r.setRegistrationId(rs.getInt("RegistrationID"));
                r.setStudentName(rs.getString("StudentName"));
                r.setSubjectName(rs.getString("SubjectName"));
                r.setRegistrationDate(rs.getDate("RegistrationDate"));
                r.setStatus(rs.getString("Status"));
                list.add(r);
            }
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn Đăng ký tín chỉ: " + e.getMessage());
        }
        return list;
    }
}
