package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Grades;

public class GradesDAO {
	public List<Grades> getAllGrades() {
        List<Grades> list = new ArrayList<>();
        String sql = "SELECT g.GradeID, stu.FullName AS StudentName, sub.SubjectName, g.MidtermScore, g.FinalScore "
                   + "FROM Grades g "
                   + "JOIN Students stu ON g.StudentID = stu.StudentID "
                   + "JOIN CourseClasses cc ON g.CourseClassID = cc.CourseClassID "
                   + "JOIN Subjects sub ON cc.SubjectID = sub.SubjectID";
                   
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Grades g = new Grades();
                g.setGradeId(rs.getInt("GradeID"));
                g.setStudentName(rs.getString("StudentName"));
                g.setSubjectName(rs.getString("SubjectName"));
                g.setMidtermScore(rs.getDouble("MidtermScore"));
                g.setFinalScore(rs.getDouble("FinalScore"));
                list.add(g);
            }
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn Bảng điểm: " + e.getMessage());
        }
        return list;
    }
}
