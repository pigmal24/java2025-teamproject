import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

//https://mvnrepository.com/artifact/javax.activation/activation/1.1 에서 jar 다운
//https://github.com/javaee/javamail/releases 에서 jar 다운

public class EmailSender {
    public static void main(String[] args) {
        // 1. 보내는 사람 Gmail 계정 정보
        final String username = "useremail@gmail.com";          // 본인 Gmail 주소
        final String password = "gmail app password";             // Gmail 앱 비밀번호

        // 2. 받는 사람 이메일 주소
        String toEmail = "useremail@gmail.com";

        // 3. SMTP 서버 설정 (Gmail 기준)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");                     // 인증 사용
        props.put("mail.smtp.starttls.enable", "true");          // TLS 보안 사용
        props.put("mail.smtp.host", "smtp.gmail.com");           // SMTP 서버
        props.put("mail.smtp.port", "587");                       // TLS 포트

        // 4. 인증 세션 생성
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 5. 이메일 메시지 객체 생성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));                     // 보내는 사람
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));                             // 받는 사람
            message.setSubject("JavaMail 테스트 이메일");                      // 제목
            message.setText("안녕하세요! 이 메일은 JavaMail API를 이용해 보낸 테스트 이메일입니다.");  // 본문

            // 6. 이메일 전송
            Transport.send(message);

            System.out.println("이메일 전송 성공!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
