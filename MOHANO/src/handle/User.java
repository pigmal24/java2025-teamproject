package handle;

/**
 * @Author : 2023007915 김준혁
 * @Project : 2025 자바 팀프로젝트
 * @프로그램_설명 : 학생의 정보를 담아두는 class(sql 정보저장	)
 * @File_Name : User.java
 * @Todo_List : sql 사용시, 데이터 받아오는 클래스(맴버만)으로 만든 다음, 상속받아서 해볼 예정
 */


public class User { // 실제로 생성하고, SQL에서 읽어오는 객체, hand로 downcasting해서 수정, 삭제, 추가 가능
	
	protected String schoolNum; // 학번
	protected String studentName; // 이름
    protected String emailAddress; // 이메일 주소
    protected TaskArr taskTable;

    // 생성자
    public User() {
    	taskTable = new TaskArr();
    }
    
    //Getter & Setter for id
    public int getId() {
        return taskTable.id;
    }
    //Getter & Setter for taskId
    public int getTaskId() {
    	return taskTable.taskId;
    }

    // Getter & Setter for 학번
    public String getSchoolNum() {
        return schoolNum;
    }

    public void setSchoolNum(String schoolNum) {
        this.schoolNum = schoolNum;
    }

    // Getter & Setter for 이름
    public String getName() {
        return studentName;
    }

    public void setName(String studentName) {
        this.studentName = studentName;
    }

    // Getter & Setter for 이메일
    public String getEmail() {
        return emailAddress;
    }

    public void setEmail(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}