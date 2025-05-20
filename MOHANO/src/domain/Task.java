package domain;


// Task 테이블의 taskId 는 primary key
// userId 는 User 테이블의 id 를 기본키로 하는 Foreign Key

import lombok.Data;

@Data
public class Task {

    // P
    // primary key
    private int taskId;
    private int userId;
    private String taskSub;
    private String taskName;
    private String deadLine;

}
