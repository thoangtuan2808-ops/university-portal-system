package dao;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    
    // Hàm này sẽ chọc xuống Database, tìm dòng có tên đăng nhập tương ứng và đóng gói thành object User
    public User getUserByUsername(String username) {
        User user = null;
        // Đảm bảo tên bảng và cột khớp với CSDL UniversityPortal của bạn
        String sql = "SELECT * FROM Users WHERE UserName = ? AND IsActive = 1"; 
        
        // Cấu trúc try-with-resources tự động đóng kết nối sau khi chạy xong
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Gắn chữ username vào dấu chấm hỏi (?) ở câu SQL trên
            ps.setString(1, username);
            
            // Thực thi và hứng kết quả trả về
            ResultSet rs = ps.executeQuery();
            
            // Nếu tìm thấy 1 dòng dữ liệu
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("UserId")); // Tên trong ngoặc kép phải gõ chính xác tên cột SQL
                user.setUserName(rs.getString("UserName"));
                user.setPasswordHash(rs.getString("PasswordHash"));
                user.setEmail(rs.getString("Email"));
                user.setRoleId(rs.getInt("RoleId"));
                user.setActive(rs.getBoolean("IsActive"));
            }
            
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy User: " + e.getMessage());
        }
        
        return user;
    }
}
