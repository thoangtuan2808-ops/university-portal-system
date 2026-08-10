package service;

public class StatsManager {
	private static int activeStudents = 0;
    private static int activeTeachers = 0;
    private static int guestCount = 0; // Giả lập số lượng người đang xem trang chủ chưa đăng nhập

    // Dùng synchronized để tránh xung đột khi nhiều người đăng nhập cùng 1 mili-giây
    public static synchronized void addStudent() { activeStudents++; }
    public static synchronized void removeStudent() { if(activeStudents > 0) activeStudents--; }
    
    public static synchronized void addTeacher() { activeTeachers++; }
    public static synchronized void removeTeacher() { if(activeTeachers > 0) activeTeachers--; }

    public static int getActiveStudents() { return activeStudents; }
    public static int getActiveTeachers() { return activeTeachers; }
    public static int getTotal() { return activeStudents + activeTeachers + guestCount; }
}
