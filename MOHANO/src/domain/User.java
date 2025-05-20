package domain;


import lombok.Data;

@Data
public class User {

    // primary key 로 사용할 id 필드 추가
    int id;
    String schoolNum;
    String emailAddress;
    String smtpPass;
    String studentName;

}
