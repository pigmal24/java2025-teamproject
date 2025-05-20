package emailsender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Scanner;

public class EmailSender {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("보내는 Gmail 주소: ");
        String senderEmailAddress = scanner.nextLine();

        System.out.print("앱 비밀번호: ");
        String senderEmailPassword = scanner.nextLine();

        System.out.print("받는 사람 이메일 주소: ");
        String receiverEmailAddress = scanner.nextLine();

        System.out.print("이메일 제목: ");
        String emailSubject = scanner.nextLine();

        System.out.print("이메일 본문: ");
        String emailContent = scanner.nextLine();

        scanner.close();
        
        new EmailSender().sendEmail(senderEmailAddress, senderEmailPassword, receiverEmailAddress, emailSubject, emailContent);
    }

    public void sendEmail(String senderEmailAddress, String senderEmailPassword, String receiverEmailAddress, String emailSubject, String emailContent) {

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
            message.setText(emailContent); // 일반 텍스트로 보내고 싶을 경우

            Transport.send(message);

            System.out.println("이메일 전송 완료 → " + receiverEmailAddress);
        } catch (MessagingException e) {
            System.out.println("이메일 전송 실패: " + e.getMessage());
        }
    }
}
