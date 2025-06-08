package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class SignInAndUpController {

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private void handleSignIn() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignIn.fxml"));
            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Sign In");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUp() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignUp.fxml"));
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Sign Up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
