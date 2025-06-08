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
import handle2.LmsCrawling;

import java.util.Optional;

public class SignInController {

    @FXML
    private TextField schoolNumField;

    @FXML
    private TextField lmsIdField;

    @FXML
    private TextField emailField;

    @FXML
    private Button gotoSignUpButton;

    @FXML
    private Button mohanoButton;
    
    @FXML 
    private TextField lmsPassField;

    @FXML
    public void handleLogin() {
    	
    
        String schoolNum = schoolNumField.getText().trim();
        String lmsIdInput = lmsIdField.getText().trim();
        String lmsPassInput = lmsPassField.getText().trim();
        String emailInput = emailField.getText().trim();

        // 필수 입력 체크
        if (schoolNum.isEmpty() || lmsIdInput.isEmpty() || lmsPassInput.isEmpty() || emailInput.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "입력 오류", "모든 필드를 채워주세요.");
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
        if (!emailInput.trim().equals(user.getEmailAddress().trim())) {
            showAlert(Alert.AlertType.ERROR, "로그인 실패", "이메일이 올바르지 않습니다.");
            return;
        }
        
        try {
            // 크롤링 시작 전 2초 대기
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        LmsCrawling lmsCrawling = new LmsCrawling(lmsIdInput, lmsPassInput);

        try {
            // 크롤링 시작 전 2초 대기
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
        try {
        	var tasks = lmsCrawling.crawling(-1);

        	if (tasks != null) { // null이 아니면 로그인 성공으로 간주
        	    showAlert(Alert.AlertType.INFORMATION, "로그인 성공", "환영합니다, " + user.getStudentName() + "님!");

        	    // 화면 전환
        	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
        	    Parent root = loader.load();
        	    HomeController homeController = loader.getController();
        	    homeController.setTasks(tasks); // tasks가 비어 있어도 정상
        	    Stage stage = (Stage) schoolNumField.getScene().getWindow();
        	    stage.setScene(new Scene(root, 600, 400));
        	    stage.setTitle("Home");
        	    return;

        	} else { // null이면 로그인 실패 또는 크롤링 오류
        	    showAlert(Alert.AlertType.ERROR, "로그인 실패", "LMS 로그인 정보가 올바르지 않습니다.");
        	    return;
        	}

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "오류", "LMS 크롤링 중 오류가 발생했습니다.");
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
