package handle2;

import javax.mail.*;
import javax.mail.internet.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;


public class EmailSender {

    // user, task 정보 받아 이메일 전송
    public void sendUserTaskEmail(User user, List<Task> todayTask, List<Task> removedTask) {
        String senderEmail = user.getEmailAddress();
        String senderPassword = user.getSmtpPass();
        String receiverEmail = senderEmail;  // 자기 자신에게 이메일 발송

 
        String subject = "과제 알림입니다."; // 기본 제목
        StringBuilder bodyBuilder = new StringBuilder();
        // 과제가 없는 경우
        if (todayTask == null || todayTask.isEmpty()) {
            bodyBuilder.append("과제가 없습니다!\n\n");
        // 과제가 존재할 경우 해당 과제들을 builder 에 추가
        } else {
            bodyBuilder.append("24시간 이내 마감 과제:\n");
            for(Task a : todayTask) {
                bodyBuilder.append(
                    "과목: " + a.getSubject() + "\n"
                    + "과제: " + a.getTitle() + "\n"
                    + "마감일: " + a.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n\n"
                );
            }
            bodyBuilder.append("기한 내에 꼭 제출해 주세요!\n\n");
        }

        // 기한이 지난 과제가 존재할 시
        if (removedTask != null && !removedTask.isEmpty()) {
            bodyBuilder.append("미제출 과제:\n");
            for(Task a : removedTask) {
                bodyBuilder.append(
                    "과목: " + a.getSubject() + "\n"
                    + "과제: " + a.getTitle() + "\n"
                    + "마감일: " + a.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n\n"
                );
            }
            bodyBuilder.append("기한이 지난 과제입니다!\n");
        }
        

        // String 으로 변환 후 body 에 저장
        String body = bodyBuilder.toString();
        // 메세지 전송
        sendEmail(senderEmail, senderPassword, receiverEmail, subject, body);
    }
 

	// 실제 이메일 전송 메서드
    public void sendEmail(String senderEmailAddress, String senderEmailPassword,
                          String receiverEmailAddress, String emailSubject, String emailContent) {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmailAddress, senderEmailPassword);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmailAddress));
            message.setSubject(emailSubject);
            message.setText(emailContent);

            Transport.send(message);

            System.out.println("이메일 전송 완료 → " + receiverEmailAddress);
        } catch (MessagingException e) {
            System.out.println("이메일 전송 실패: 메일 주소와 앱 비밀번호를 확인해주세요.");//e.getMessage());
        }
    }

}