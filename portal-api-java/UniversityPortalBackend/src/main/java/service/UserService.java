package service;

import java.util.Date;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import dao.UserDAO;
import entity.User;

public class UserService {
    
    // Gọi DAO để chuẩn bị sẵn công cụ truy xuất dữ liệu
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // Hàm xử lý nghiệp vụ đăng nhập
    public User login(String username, String password) {
        // 1. Yêu cầu DAO đi tìm user theo username
        User user = userDAO.getUserByUsername(username);

        // 2. Kiểm tra xem tài khoản có tồn tại không
        if (user == null) {
            System.out.println("❌ Đăng nhập thất bại: Tài khoản không tồn tại.");
            return null;
        }

        // 3. Kiểm tra trạng thái hoạt động (RBAC cơ bản)
        if (!user.isActive()) {
            System.out.println("❌ Đăng nhập thất bại: Tài khoản của bạn đã bị khóa.");
            return null;
        }

        // 4. Kiểm tra mật khẩu
        // (Lưu ý: Tạm thời so sánh chuỗi trực tiếp. Khi ráp API thực tế sẽ thay bằng hàm kiểm tra Hash của thư viện BCrypt)
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            System.out.println("✅ Đăng nhập thành công! Chào mừng: " + user.getUserName());
            System.out.println("🔑 Quyền hạn (Role ID): " + user.getRoleId());
            if (user.getRoleId() == 3) {
                StatsManager.addStudent(); 
            } else if (user.getRoleId() == 2) {
                StatsManager.addTeacher();
            }
            return user;
        } else {
            System.out.println("❌ Đăng nhập thất bại: Sai mật khẩu.");
            return null;
        }
    }
    //tạo token sau khi login
    public String generateToken(User user) {
    	Algorithm algorithm = Algorithm.HMAC256("ChuoiKhoaBiMatCuaRiengBan_KhongDuocDeLo");//kí tự bí mật
    	long expirationTime=2*3600*1000;//thời gian hết hạn token
    	String token = JWT.create()
    			.withIssuer("UniversityPortal")
    			.withClaim("username", user.getUserName())
    			.withClaim("roleId", user.getRoleId())
    			.withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))
    			.sign(algorithm);
    	return token;
    }
    //kiểm tra token và quyền hạn
    public boolean checkAuthorization(String token, int requiredRoleId) {
    	try {
			Algorithm algorithm = Algorithm.HMAC256("ChuoiKhoaBiMatCuaRiengBan_KhongDuocDeLo");
			//xác thực tính hợp lệ token
			DecodedJWT jwt = JWT.require(algorithm)
					.withIssuer("UniversityPortal")
					.build()
					.verify(token);//văng nếu token không đúng
			int userRoleId = jwt.getClaim("roleId").asInt();//rút roleid trong token kiểm tra
			if(userRoleId==requiredRoleId) {
				return true;
			}
			else {
				System.out.println("Từ chối truy cập: Không đủ thẩm quyền.");
				return false;
			}
		} catch (JWTCreationException exception) {
			// TODO: handle exception
			System.out.println("Từ chối truy cập: Token không hợp lệ hoặc hết hạn.");
			return false;
		}
    }
    public List<User> getAllUsers(){
    	return userDAO.getAllUsers();
    }
    public boolean lockUser(int userId) {
    	return userDAO.lockUser(userId);
    }
    public boolean addUser(User user) {
    	return userDAO.addUser(user);
    }
    public boolean updateUser(User user) {
    	return userDAO.updateUser(user);
    }
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
}
