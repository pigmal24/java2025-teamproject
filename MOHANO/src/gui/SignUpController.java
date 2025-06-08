package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import handle2.User;
import respository.UserRepository;


// 회원가입 화면 컨트롤러
public class SignUpController {

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField smtpPassField;

    @FXML
    private TextField lmsIdField;

    @FXML
    private TextField lmsPassField;

    @FXML
    private Button gotoSignInButton;

    @FXML
    private Button mohanoButton;

    // 회원가입 버튼 눌렀을 때 실행됨
    @FXML
    private void handleSignUp() {
        String studentId = studentIdField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String smtpPass = smtpPassField.getText().trim();
        
        // 추가 부분
        String lmsId = lmsIdField.getText().trim();
        String lmsPass = lmsPassField.getText().trim();

        // 간단한 필수 입력 체크
        if (studentId.isEmpty() || name.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("입력 오류");
            alert.setHeaderText(null);
            alert.setContentText("학번, 이름, 이메일은 필수 입력사항입니다.");
            alert.showAndWait();
            return;
        }

        User user = new User();
        user.setSchoolNum(studentId);
        user.setStudentName(name);
        user.setEmailAddress(email);
        user.setSmtpPass(smtpPass);
        // 추가
        user.setLmsId(lmsId);
        user.setLmsPass(lmsPass);
      
        UserRepository.getInstance().save(user);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignUpSuccess.fxml"));
            Stage stage = (Stage) gotoSignInButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));  // SignUpSuccess.fxml에 맞는 크기 설정
            stage.setTitle("Sign Up Success");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 회원가입 후 로그인 화면으로 이동 (원하면)
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignIn.fxml"));
            Stage stage = (Stage) gotoSignInButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 500));
            stage.setTitle("Sign In");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGoToSignIn() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignIn.fxml"));
            Stage stage = (Stage) gotoSignInButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Sign In");
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
}
