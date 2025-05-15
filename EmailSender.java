import javax.mail.*;
import javax.mail.internet.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.*;

public class EmailSender {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("마감 기한 입력 (yyyy-MM-dd): ");
        String deadlineStr = scanner.nextLine();

        // 1. 날짜 파싱 및 3일 전 계산
        LocalDate deadlineDate = LocalDate.parse(deadlineStr, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate sendDate = deadlineDate.minusDays(3);
        System.out.println("이메일 전송 예정일: " + sendDate);

        // 현재 시각과 보낼 시각 계산 (보낼 시각을 sendDate의 오전 9시로 설정)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sendDateTime = sendDate.atTime(9, 0);

        long delay = Duration.between(now, sendDateTime).toMillis();

        if (delay <= 0) {
            System.out.println("이미 3일 전이 지났거나 오늘입니다. 바로 이메일을 전송합니다.");
            sendEmail();  // 즉시 전송
        } else {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(() -> {
                sendEmail();
                scheduler.shutdown();
            }, delay, TimeUnit.MILLISECONDS);
            System.out.println("이메일이 예약되었습니다.");
        }
    }

    public static void sendEmail() {
        final String username = "userEMAIL@gmail.com";      // 본인 Gmail 주소
        final String password = "APP PASSWORD";       // 앱 비밀번호

        String toEmail = "userEMAIL@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject("마감 3일 전 알림 이메일");
            message.setText("안녕하세요! 마감일이 3일 남았습니다. 일정 확인 부탁드립니다.");

            Transport.send(message);

            System.out.println("이메일 전송 성공!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
