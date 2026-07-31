package entity;
import java.sql.Timestamp;
public class User {
	private int userId;
    private String userName;
    private String passwordHash;
    private String email;
    private int roleId;
    private boolean isActive;
    private Timestamp createdAt;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int userId, String userName, String passwordHash, String email, int roleId, boolean isActive,
			Timestamp createdAt) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.passwordHash = passwordHash;
		this.email = email;
		this.roleId = roleId;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
    
}
