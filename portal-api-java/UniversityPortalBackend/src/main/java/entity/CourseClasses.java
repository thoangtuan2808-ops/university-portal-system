package entity;

public class CourseClasses {
	private int courseClassId;
    private String subjectName;
    private String semesterName;
    private String teacherName;
    private int maxCapacity;

    public CourseClasses() {}

    public int getCourseClassId() { return courseClassId; }
    public void setCourseClassId(int courseClassId) { this.courseClassId = courseClassId; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
    
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
}
