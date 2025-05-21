package handle;

/**
 * @Author : 2023007915 김준혁
 * @Project : 2025 자바 팀프로젝트
 * @프로그램_설명 : 학생의 정보를 담아두는 class(arrayList형)
 * @File_Name : StudentTask.java
 * @Todo_List : sql 사용시, 데이터 받아오는 클래스(맴버만)으로 만든 다음, 상속받아서 해볼 예정
 */
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;

public class User {

    //private int id; // 기본키로 사용
	private int id; //user table id
	private int taskId; // user table에 join한 task table id
    private String schoolNum; // 학번
    private String studentName; // 이름
    private String emailAddress; // 이메일 주소
    private ArrayList<Task> allTask; // 전체 과제 목록
    private ArrayList<Task> todayTask; // 오늘 할 과제 목록 (24시간 이내 마감)

    // 생성자
    public User(String schoolNum, String studentName, String emailAddress) {
        this.schoolNum = schoolNum;
        this.studentName = studentName;
        this.emailAddress = emailAddress;
        id = 0;
        taskId = 0;
        allTask = new ArrayList<>();
        todayTask = new ArrayList<>();
    }
    // Getter & Setter for id
    public void setId(int id) {
    	this.id = id;
    }
    public int getId() {
    	return id;
    }
    // Getter & Setter for taskId
    public void setTaskId(int taskId) {
    	this.taskId = taskId;
    }
    public int getTaskxId() {
    	return taskId;
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

    // 과제 추가 및 todayTask 자동 갱신
    public void addAssignmentToAll(Task assignment) {
        allTask.add(assignment);
        updateTodayTask();
    }
    //과제 삭제 및 todayTask 자동 갱신 
    public Task removeAssignmentToAll(int index) {
    	Task res = allTask.get(index);
    	allTask.remove(index);
    	updateTodayTask();
    	return res;
    }
    //특정 index 과제 return
    public Task get(int index) {
    	return allTask.get(index);
    }
    //특정 index 과제 수정
    public void set(int index, Task chTask) {
    	allTask.set(index, chTask);
    }

    // todayTask 갱신 (24시간 이내 마감 과제)
    public void updateTodayTask() {
        todayTask.clear();
        LocalDateTime now = LocalDateTime.now();
        for (Task a : allTask) {
            Duration duration = Duration.between(now, a.getDeadline());
            if (!duration.isNegative() && duration.toHours() <= 24) {
                todayTask.add(a);
            }
        }
    }
 // 지난 과제 자동 삭제
    public void removeExpiredAssignments() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Task> iterator = allTask.iterator();

        while (iterator.hasNext()) {
            Task a = iterator.next();
            if (a.getDeadline().isBefore(now)) {
                iterator.remove();
            }
        }
        // todayTask도 갱신해줘야 함
        updateTodayTask();
    }

    // 전체 과제 출력
    public void printAllTasks() {
    	int i = 0;
        System.out.println("전체 과제 목록:");
        for (Task a : allTask) {
            System.out.printf("[%2d]: ",i);
            System.out.println(a);
            i++;
        }
    }

    // 오늘 할 과제 출력
    public void printTodayTasks() {
    	int i = 0;
        System.out.println("오늘 할 과제 목록 (24시간 이내 마감):");
        for (Task a : todayTask) {
            System.out.printf("[%2d]: ",i);
            System.out.println(a);
            i++;
        }
    }
    @Override
    public String toString() {
    	return "학번: "+schoolNum+"이름: "+studentName+"메일 주소: "+emailAddress+"전체 과제 수: "+allTask.size()+"오늘 과제 수:"+todayTask.size();
    }
}