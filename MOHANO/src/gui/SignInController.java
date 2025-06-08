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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // 필수 입력 체크
        if (schoolNum.isEmpty() || emailInput.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "입력 오류", "모든 항목을 채워주세요.");
            return;
        }

        // 학번으로 사용자 존재 여부 확인 (DB 조회)
        Optional<User> optionalUser = UserRepository.getInstance().findByLoginId(schoolNum);
        if (optionalUser.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "로그인 실패", "등록되지 않은 학번입니다.");
            return;
        }

        User user = optionalUser.get();

        // 이메일 일치 여부 확인 (DB 값과 비교)
        if (!emailInput.equals(user.getEmailAddress())) {
            showAlert(Alert.AlertType.ERROR, "로그인 실패", "이메일이 올바르지 않습니다.");
            return;
        }

        // User에서 LMS 아이디/비밀번호 읽기
        String lmsIdInput = user.getLmsId();
        String lmsPassInput = user.getLmsPass();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LmsCrawling lmsCrawling = new LmsCrawling(lmsIdInput, lmsPassInput);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Task> tasks = null;

        try {
            tasks = lmsCrawling.crawling(-1);

            if (tasks != null) { // 로그인 성공
                showAlert(Alert.AlertType.INFORMATION, "로그인 성공", "환영합니다, " + user.getStudentName() + "님!");

                // 오늘 마감 과제 리스트 추출
                List<Task> todayTask = tasks.stream()
                        .filter(task -> {
                            LocalDateTime now = LocalDateTime.now();
                            LocalDateTime deadline = task.getDeadline();
                            return !deadline.isBefore(now) && deadline.isBefore(now.plusDays(1));
                        })
                        .collect(Collectors.toList());

                // 마감이 지난 과제 리스트 추출
                List<Task> removedTask = tasks.stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());

                // 이메일 전송
                try {
                    EmailSender emailSender = new EmailSender();
                    emailSender.sendUserTaskEmail(user, todayTask, removedTask);
                } catch (Exception e) {
                    System.out.println("이메일 전송 중 오류: " + e.getMessage());
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
                Parent root = loader.load();
                HomeController homeController = loader.getController();

                homeController.setTasks(tasks);
                //homeController.setUrgentTasks(TaskRepository.getInstance().findByUserIdTaskAll(user));
                homeController.setUser(user);

                Stage stage = (Stage) schoolNumField.getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400));
                stage.setTitle("Home");
                return;

            } else {
                showAlert(Alert.AlertType.ERROR, "로그인 실패", "LMS 로그인 정보가 올바르지 않습니다.");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
                Parent root = loader.load();
                HomeController homeController = loader.getController();

                homeController.setTasks(null);
                //homeController.setPeronalTasks(TaskRepository.getInstance().findByUserIdTaskAll(user));
                homeController.setUser(user);

                Stage stage = (Stage) schoolNumField.getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400));
                stage.setTitle("Home");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "오류", "LMS 크롤링 중 오류가 발생했습니다.");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
                Parent root = loader.load();
                HomeController homeController = loader.getController();

                homeController.setTasks(tasks);
                //homeController.setUrgentTasks(TaskRepository.getInstance().findByUserIdTaskAll(user));
                homeController.setUser(user);

                Stage stage = (Stage) schoolNumField.getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400));
                stage.setTitle("Home");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
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
