package withoutDB;

/**
 * @Author : 2023007915 김준혁
 * @Project : 자바 팀프로젝트 MOHANO
 * @프로그램_설명 : 과제 정보를 담아두는 클래스(arrayList형)
 * @File_Name : Assignment.java
 * @Todo_List : deadLine입력, 추가 시 예외처리 필요(LocalDateTime형태)
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Assignment {
    private String subject;
    private String title;
    private LocalDateTime deadline;  // 시간까지 포함

    public Assignment(String subject, String title, String deadlineStr) {
        this.subject = subject;
        this.title = title;
        this.deadline = LocalDateTime.parse(deadlineStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
    
    public void setSubject(String sub) {
    	this.subject = sub;
    	System.out.println("과목명 수정 완료");
    }
    
    public void setTitle(String title) {
    	this.title = title;
    	System.out.println("과제명 수정 완료");
    }

    public void setDeadline(String deadlineStr) {
        this.deadline = LocalDateTime.parse(deadlineStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    	System.out.println("제출 기한 수정 완료");
    }

    @Override
    public String toString() {
        return "과목: " + subject + ", 과제: " + title + ", 제출기한: " + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

