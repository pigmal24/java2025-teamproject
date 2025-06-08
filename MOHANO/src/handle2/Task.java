package handle2;

//import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Data
public class Task {

    private int taskId; // PK
    private int userId; // FK ( USER 테이블의 PK)
    // private int id;
    private String subject;
    private String title;
   private LocalDateTime deadline;  // 시간까지 포함
   //기본 생성자
   public Task() {
      
   }
    public Task(int userId, String subject, String title, String deadLine) {
        this.userId = userId;
        this.subject = subject;
        this.title = title;
        this.deadline = LocalDateTime.parse(deadLine, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    //  this.deadline = LocalDateTime.parse(deadlineStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    
    
    public int getTaskId() {
      return taskId;
   }

   public void setTaskId(int taskId) {
      this.taskId = taskId;
   }

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }


    public void setSubject(String sub) {
        this.subject = sub;
    }
    
    public String getSubject() {
       return subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
       return title;
    }
    
    
    public void setDeadline(String deadlineStr) {
        this.deadline = LocalDateTime.parse(deadlineStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    public LocalDateTime getDeadline() {
       return deadline;
    }
    // 오류나면 이거부터 삭제해주세요
    public String getDeadlineString() {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
       return deadline.format(formatter);
    }

    @Override
    public String toString() {
        return "taskId: " + taskId + " 과목: " + subject + ", 과제: " + title + ", 제출기한: " + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}