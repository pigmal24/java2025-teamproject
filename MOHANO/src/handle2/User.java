package handle2;


/**
 * @Author : 2023007915 김준혁
 * @Project : 2025 자바 팀프로젝트
 * @프로그램_설명 : 학생의 정보를 담아두는 class(sql 정보저장	)
 * @File_Name : User.java
 * @Todo_List : sql 사용시, 데이터 받아오는 클래스(맴버만)으로 만든 다음, 상속받아서 해볼 예정
 */


public class User { // 실제로 생성하고, SQL에서 읽어오는 객체, hand로 downcasting해서 수정, 삭제, 추가 가능

	protected int id;
    protected String schoolNum; // 학번
    protected String emailAddress; // 이메일 주소
    protected String smtpPass;
    protected String studentName; // 이름
    protected String lmsId; // lms id
    protected String lmsPass; //lms password

    
    // 생성자
    public User() {
    }
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSchoolNum() {
		return schoolNum;
	}
	public void setSchoolNum(String schoolNum) {
		this.schoolNum = schoolNum;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getSmtpPass() {
		return smtpPass;
	}
	public void setSmtpPass(String smtpPass) {
		this.smtpPass = smtpPass;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getLmsId() {
		return lmsId;
	}
	public void setLmsId(String lmsId) {
		this.lmsId = lmsId;
	}
	public String getLmsPass() {
		return lmsPass;
	}
	public void setLmsPass(String lmsPass) {
		this.lmsPass = lmsPass;
	}
	
}