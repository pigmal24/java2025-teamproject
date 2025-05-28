package handle2;

import javax.mail.*;
import javax.mail.internet.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.*;


import java.util.Properties;

public class EmailSender {

    // user, task 정보 받아 이메일 전송
    public void sendUserTaskEmail(User user, Task task) {
        String senderEmail = user.getEmailAddress();
        String senderPassword = user.getSmtpPass();
        String receiverEmail = senderEmail;  // 자기 자신에게 이메일 발송

        String subject = "[과제 안내] " + task.getTitle();
        String body = "다음 과제가 등록되었습니다.\n"
                    + "과목: " + task.getSubject() + "\n"
                    + "과제: " + task.getTitle() + "\n"
                    + "마감일: " + task.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n\n"
                    + "기한 내에 꼭 제출해 주세요!\n";

        sendEmail(senderEmail, senderPassword, receiverEmail, subject, body);
    }
    
    public static void main(String[] args) {
	
    	new EmailSender().sendEmail(senderEmail, senderPassword, receiverEmail, subject, body);

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
            System.out.println("이메일 전송 실패: " + e.getMessage());
        }
    }

}