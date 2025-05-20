package domain;


import lombok.Data;

@Data
public class TaskJoinUser {

   int id;
   int taskId;
   String taskSub;
   String taskName;
   String deadLine;
   String schoolNum;
   String emailAddress;
   String smtpPass;
   String studentName;

}
