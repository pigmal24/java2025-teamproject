package withoutDB;

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

public class StudentTask {
    private String studentId;                  // 학번
    private String name;                       // 이름
    private String email;                      // 이메일 주소
    private ArrayList<Assignment> allTask;     // 전체 과제 목록
    private ArrayList<Assignment> todayTask;   // 오늘 할 과제 목록 (24시간 이내 마감)

    // 생성자
    public StudentTask(String studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.allTask = new ArrayList<>();
        this.todayTask = new ArrayList<>();
    }

    // Getter & Setter for 학번
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    // Getter & Setter for 이름
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter & Setter for 이메일
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 과제 추가 및 todayTask 자동 갱신
    public void addAssignmentToAll(Assignment assignment) {
        allTask.add(assignment);
        updateTodayTask();
    }
    //과제 삭제 및 todayTask 자동 갱신 
    public Assignment removeAssignmentToAll(int index) {
    	Assignment res = allTask.get(index);
    	allTask.remove(index);
    	updateTodayTask();
    	return res;
    }
    //특정 index 과제 return
    public Assignment get(int index) {
    	return allTask.get(index);
    }
    //특정 index 과제 수정
    public void set(int index, Assignment chTask) {
    	allTask.set(index, chTask);
    }

    // todayTask 갱신 (24시간 이내 마감 과제)
    public void updateTodayTask() {
        todayTask.clear();
        LocalDateTime now = LocalDateTime.now();
        for (Assignment a : allTask) {
            Duration duration = Duration.between(now, a.getDeadline());
            if (!duration.isNegative() && duration.toHours() <= 24) {
                todayTask.add(a);
            }
        }
    }
 // 지난 과제 자동 삭제
    public void removeExpiredAssignments() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Assignment> iterator = allTask.iterator();

        while (iterator.hasNext()) {
            Assignment a = iterator.next();
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
        for (Assignment a : allTask) {
            System.out.printf("[%2d]: ",i);
            System.out.println(a);
            i++;
        }
    }

    // 오늘 할 과제 출력
    public void printTodayTasks() {
    	int i = 0;
        System.out.println("오늘 할 과제 목록 (24시간 이내 마감):");
        for (Assignment a : todayTask) {
            System.out.printf("[%2d]: ",i);
            System.out.println(a);
            i++;
        }
    }
}

