package entity;

public class Curriculums {
	private int CurriculumID;
	private String MajorName;
	private String SubjectName;
	private int Credits;
	private int ExpectedSemester;
	public Curriculums() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getCurriculumID() {
		return CurriculumID;
	}
	public void setCurriculumID(int curriculumID) {
		CurriculumID = curriculumID;
	}
	public String getMajorName() {
		return MajorName;
	}
	public void setMajorName(String majorName) {
		MajorName = majorName;
	}
	public String getSubjectName() {
		return SubjectName;
	}
	public void setSubjectName(String subjectName) {
		SubjectName = subjectName;
	}
	public int getCredits() {
		return Credits;
	}
	public void setCredits(int credits) {
		Credits = credits;
	}
	public int getExpectedSemester() {
		return ExpectedSemester;
	}
	public void setExpectedSemester(int expectedSemester) {
		ExpectedSemester = expectedSemester;
	}
	
}
