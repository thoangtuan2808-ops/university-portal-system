package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Chuỗi kết nối đến CSDL UniversityPortal trên máy của bạn
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=UniversityPortal;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    
    public static Connection getConnection() throws SQLException {
        try {
            // Nạp Driver của SQL Server vào bộ nhớ
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        // Trả về luồng kết nối
        return DriverManager.getConnection(URL);
    }
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("✅ Kết nối Database UniversityPortal thành công rực rỡ!");
                // Tạo chuỗi mã hóa thực tế cho mật khẩu "admin123"
                String hashThucTe = org.mindrot.jbcrypt.BCrypt.hashpw("admin123", org.mindrot.jbcrypt.BCrypt.gensalt(12));
                System.out.println("Chuỗi Hash của admin123 là: " + hashThucTe);
                conn.close(); // Test xong thì đóng kết nối
            }
        } catch (SQLException e) {
            System.out.println("❌ Kết nối thất bại. Lỗi: " + e.getMessage());
        }
    }
}
