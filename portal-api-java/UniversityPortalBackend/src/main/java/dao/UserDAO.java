package dao;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	public User getUserById(int userId) {
	    String sql = "SELECT UserID, UserName, Email, RoleID, IsActive FROM Users WHERE UserID = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setInt(1, userId);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                User user = new User();
	                user.setUserId(rs.getInt("UserID"));
	                user.setUserName(rs.getString("UserName"));
	                // Không cần lấy PasswordHash lên giao diện để bảo mật
	                user.setEmail(rs.getString("Email"));
	                user.setRoleId(rs.getInt("RoleID"));
	                user.setActive(rs.getBoolean("IsActive"));
	                return user;
	            }
	        }
	    } catch (Exception e) { 
	        System.out.println("Lỗi lấy User detail: " + e.getMessage()); 
	    }
	    return null;
	}
    // Hàm này sẽ chọc xuống Database, tìm dòng có tên đăng nhập tương ứng và đóng gói thành object User
    public User getUserByUsername(String username) {
        User user = null;
        // Đảm bảo tên bảng và cột khớp với CSDL UniversityPortal của bạn
        String sql = "SELECT * FROM Users WHERE UserName = ?"; 
        
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
    public List<User> getAllUsers(){
    	List<User> list = new ArrayList<>();
    	String sql = "SELECT UserID, UserName, Email, RoleID, IsActive, CreateAt FROM Users";
    	try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("UserID"));
				user.setUserName(rs.getString("UserName"));
				user.setEmail(rs.getString("Email"));;
				user.setRoleId(rs.getInt("RoleID"));
				user.setActive(rs.getBoolean("IsActive"));
				user.setCreatedAt(rs.getTimestamp("CreateAt"));
				list.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi lấy danh sách User: " +e.getMessage());
		}
    	return list;
    }
    public boolean lockUser(int userId) {
    	String sql = "UPDATE Users SET IsActive = 0 WHERE UserID=?";
    	try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			int rowAffect = ps.executeUpdate();
			return rowAffect>0;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi khóa User: " + e.getMessage());
		}
    	return false;
    }
    public boolean addUser(User user) {
    	String sql = "INSERT INTO Users (UserName, PasswordHash, Email, RoleID, IsActive) VALUES (?, ?, ?, ?, ?)";
    	try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, user.getUserName());
			String hashedPass = org.mindrot.jbcrypt.BCrypt.hashpw(user.getPasswordHash(), org.mindrot.jbcrypt.BCrypt.gensalt());
			ps.setString(2, hashedPass);
			ps.setString(3, user.getEmail());
			ps.setInt(4, user.getRoleId());
			ps.setBoolean(5, user.isActive());
			return ps.executeUpdate()>0;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi thêm User: "+e.getMessage());
		}
    	return false;
    }
    public boolean updateUser(User user) {
    	String sql = "UPDATE Users SET PasswordHash=?, Email=?, RoleID=?, IsActive=? WHERE UserID=?";
    	try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
    		String hashedPass = org.mindrot.jbcrypt.BCrypt.hashpw(user.getPasswordHash(), org.mindrot.jbcrypt.BCrypt.gensalt());
    		ps.setString(1, hashedPass);
			ps.setString(2, user.getEmail());
			ps.setInt(3, user.getRoleId());
			ps.setBoolean(4, user.isActive());
			ps.setInt(5, user.getUserId());
			return ps.executeUpdate()>0;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi cập nhật User: "+e.getMessage());
		}
    	return false;
    }
}
