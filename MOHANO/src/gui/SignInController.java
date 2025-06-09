package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import handle2.User;
import respository.UserRepository;
import respository.TaskRepository;
import handle2.LmsCrawling;
import handle2.EmailSender;
import handle2.Task;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
//
public class SignInController {

    @FXML
    private TextField schoolNumField;

    @FXML
    private TextField emailField;

    @FXML
    private Button gotoSignUpButton;

    @FXML
    private Button mohanoButton;

    @FXML
    public void handleLogin() {
        String schoolNum = schoolNumField.getText().trim();
        String emailInput = emailField.getText().trim();

        if (schoolNum.isEmpty() || emailInput.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "입력 오류", "모든 항목을 채워주세요.");
            return;
        }

        Optional<User> optionalUser = UserRepository.getInstance().findByLoginId(schoolNum);
        if (optionalUser.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "로그인 실패", "등록되지 않은 학번입니다.");
            return;
        }

        User user = optionalUser.get();

        if (!emailInput.equals(user.getEmailAddress())) {
            showAlert(Alert.AlertType.ERROR, "로그인 실패", "이메일이 올바르지 않습니다.");
            return;
        }

        // --- LMS 크롤링 ---
        String lmsIdInput = user.getLmsId();
        String lmsPassInput = user.getLmsPass();

        LmsCrawling lmsCrawling = new LmsCrawling(lmsIdInput, lmsPassInput);
        ArrayList<Task> lmsTasks = lmsCrawling.crawling(user.getId());

        TaskRepository repo = TaskRepository.getInstance();

        if (lmsTasks != null) {
            List<Task> existingTasks = repo.findByUserIdTaskAll(user);
            for (Task task : lmsTasks) {
                boolean isDuplicate = existingTasks.stream().anyMatch(t ->
                        t.getSubject().equals(task.getSubject()) &&
                        t.getTitle().equals(task.getTitle()) &&
                        t.getDeadline().equals(task.getDeadline())
                );
                if (!isDuplicate) {
                    repo.save(task, user);
                }
            }
        }

        // 기한이 지난 과제를 removedTask 에 저장
        List<Task> removedTask = repo.removePastTasksAll(user);
        try {
            // 홈 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();

            // 이메일 전송
            List<Task> allTasks = repo.findByUserIdTaskAll(user);

            List<Task> todayTask = allTasks.stream()
                    .filter(task -> {
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime deadline = task.getDeadline();
                        return !deadline.isBefore(now) && deadline.isBefore(now.plusDays(1));
                    })
                    .collect(Collectors.toList());
  
            try {
                EmailSender emailSender = new EmailSender();
                emailSender.sendUserTaskEmail(user, todayTask, removedTask);
            } catch (Exception e) {
                System.out.println("이메일 전송 중 오류: " + e.getMessage());
            }

            // 데이터 모두 준비 완료 후 UI 렌더링
            homeController.loadWithTasks(user, allTasks);

            Stage stage = (Stage) schoolNumField.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.setTitle("Home");
            stage.show();

            showAlert(Alert.AlertType.INFORMATION, "로그인 성공", "환영합니다, " + user.getStudentName() + "님!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "오류", "LMS 크롤링 또는 화면 로딩 중 오류가 발생했습니다.");
        }
    }

    @FXML
    public void handleGoToSignUp() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignUp.fxml"));
            Stage stage = (Stage) gotoSignUpButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Sign Up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSignInAndUp() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignInAndUp.fxml"));
            Stage stage = (Stage) mohanoButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Sign In & Up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
