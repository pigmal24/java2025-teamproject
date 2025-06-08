package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SignUpSuccessController {

    @FXML
    private Button signInButton;

    @FXML
    private void handleSignInButton() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignIn.fxml"));
            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Sign In");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
