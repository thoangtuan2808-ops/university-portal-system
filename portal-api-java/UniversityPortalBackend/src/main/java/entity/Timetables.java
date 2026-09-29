package entity;

public class Timetables {
	private int scheduleId;
    private String subjectName;
    private String roomName;
    private String dayOfWeek;
    private int shift;

    public Timetables() {}

    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    
    public int getShift() { return shift; }
    public void setShift(int shift) { this.shift = shift; }
}
