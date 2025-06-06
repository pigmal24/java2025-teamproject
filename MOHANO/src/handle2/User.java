package handle2;

import lombok.*;

@Data
public class User { // 실제로 생성하고, SQL에서 읽어오는 객체, hand로 downcasting해서 수정, 삭제, 추가 가능

	protected int id;
    protected String schoolNum; // 학번
    protected String emailAddress; // 이메일 주소
    protected String smtpPass;
    protected String studentName; // 이름

    
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
	
}