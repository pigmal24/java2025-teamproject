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

import java.util.Optional;

public class EditProfileController {

    @FXML private TextField schoolNumField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField smtpPassField;
    @FXML private TextField lmsIdField;
    @FXML private TextField lmsPassField;
    @FXML private Button saveInfoButton;
    @FXML private Button mohanoButton;

    private User user;

    // 외부에서 User 정보 전달받아 화면에 표시
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            schoolNumField.setText(user.getSchoolNum());
            nameField.setText(user.getStudentName());
            emailField.setText(user.getEmailAddress());
            smtpPassField.setText(user.getSmtpPass());
            lmsIdField.setText(user.getLmsId());
            lmsPassField.setText(user.getLmsPass());
        }
    }

    @FXML
    private void handleSaveInfo() {
        String schoolNum = schoolNumField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String smtpPass = smtpPassField.getText().trim();
        String lmsId = lmsIdField.getText().trim();
        String lmsPass = lmsPassField.getText().trim();

        if (schoolNum.isEmpty() || name.isEmpty() || email.isEmpty() || smtpPass.isEmpty() || lmsId.isEmpty() || lmsPass.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "입력 오류", "모든 항목을 채워주세요.");
            return;
        }

        UserRepository repo = UserRepository.getInstance();

        // 이메일로 User 찾기 (findAll을 사용해 직접 검색)
        Optional<User> optionalUser = repo.findAll().stream()
                .filter(u -> email.equals(u.getEmailAddress()))
                .findFirst();

        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            repo.updateSchoolNum(foundUser, schoolNum);
            repo.updateStudentName(foundUser, name);
            repo.updateEmailAddress(foundUser, email);
            repo.updateSmtpPass(foundUser, smtpPass);
            repo.updateLmsId(foundUser, lmsId);
            repo.updateLmsPass(foundUser, lmsPass);

            showAlert(Alert.AlertType.INFORMATION, "저장 완료", "회원정보가 성공적으로 수정되었습니다.");

            // 저장 후 Home 화면으로 이동
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
                Parent root = loader.load();
                HomeController homeController = loader.getController();
                // 필요시 homeController.setUser(foundUser);
                Stage stage = (Stage) saveInfoButton.getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400));
                stage.setTitle("Home");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "수정 실패", "입력한 이메일에 해당하는 회원이 존재하지 않습니다.");
        }
    }

    @FXML
    public void handleMohanoButton() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignIn.fxml"));
            Stage stage = (Stage) mohanoButton.getScene().getWindow();
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

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
