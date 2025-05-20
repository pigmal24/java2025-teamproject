package handle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
	private String taskSub;
	private String taskName;
	private LocalDateTime deadline;  // 시간까지 포함
	public Task(String taskSub, String taskName, String deadLineStr) {
        this.taskSub = taskSub;
        this.taskName = taskName;
        this.deadline = LocalDateTime.parse(deadLineStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	public String getSubject() {
        return taskSub;
    }

    public String getTitle() {
        return taskName;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
    
    public void setTaskSub(String sub) {
    	this.taskSub = sub;
    	//System.out.println("과목명 수정 완료");
    }
    
    public void setTaskName(String title) {
    	this.taskName = title;
    	//System.out.println("과제명 수정 완료");
    }

    public void setDeadLine(String deadlineStr) {
        this.deadline = LocalDateTime.parse(deadlineStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    	//System.out.println("제출 기한 수정 완료");
    }
    @Override
    public String toString() {
        return "과목: " + taskSub + ", 과제: " + taskName + ", 제출기한: " + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
