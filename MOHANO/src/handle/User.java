package handle;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class User {
	private int id; // user table id
	private int taskId; // user table에 join한 task table id
	private String studentName; //이름
	private String schoolNum; //학번
	private String smtpPass;//smtp에 들어가는 비번
	private String emailAddress;//메일 주소
	private ArrayList<Task>todayTask;//모든 과제 목록
	private ArrayList<Task>allTask;//오늘 과제 목록
	public User(String name, String schoolNum, String email) {
		this.studentName = name;
		this.emailAddress = email;
		this.schoolNum = schoolNum;
		this.id = 0;
		this.taskId = 0;
		this.smtpPass = "";
		this.allTask = new ArrayList<>();
		this.todayTask = new ArrayList<>();
	}
    // Getter & Setter for 학번
    public String getSchoolNum() {
        return schoolNum;
    }

    public void setSchoolNum(String schoolNum) {
        this.schoolNum = schoolNum;
    }

    // Getter & Setter for 이름
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Getter & Setter for 이메일
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    // Getter & Setter for smtp를 위한 email 내부 비밀번호
    public String getSmtpPass() {
    	return smtpPass;
    }
    
    public void setSmtpPass(String smtpPass) {
    	this.smtpPass = smtpPass;
    }

    // 과제 추가 및 todayTask 자동 갱신
    public void addTaskToAll(Task task) {
        allTask.add(task);
        updateTodayTask();
    }
    //과제 삭제 및 todayTask 자동 갱신 
    public Task removeTaskToAll(int index) {
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
    public void removeExpiredTasks() {
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
}

