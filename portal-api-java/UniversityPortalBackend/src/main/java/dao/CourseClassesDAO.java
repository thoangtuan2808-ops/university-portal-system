package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.CourseClasses;

public class CourseClassesDAO {
	public List<CourseClasses> getAllCourseClasses() {
        List<CourseClasses> list = new ArrayList<>();
        String sql = "SELECT cc.CourseClassID, s.SubjectName, sem.SemesterName, "
                   + "t.FullName AS TeacherName, cc.MaxCapacity "
                   + "FROM CourseClasses cc "
                   + "JOIN Subjects s ON cc.SubjectID = s.SubjectID "
                   + "JOIN Semesters sem ON cc.SemesterID = sem.SemesterID "
                   + "LEFT JOIN Teachers t ON cc.TeacherID = t.TeacherID";
                   
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                CourseClasses c = new CourseClasses();
                c.setCourseClassId(rs.getInt("CourseClassID"));
                c.setSubjectName(rs.getString("SubjectName"));
                c.setSemesterName(rs.getString("SemesterName"));
                c.setTeacherName(rs.getString("TeacherName"));
                c.setMaxCapacity(rs.getInt("MaxCapacity"));
                list.add(c);
            }
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn Lớp học phần: " + e.getMessage());
        }
        return list;
    }
}
