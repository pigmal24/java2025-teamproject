package gui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartViewController {

    @FXML
    private Pane rootPane;

    @FXML
    public void initialize() {
        // 3초 대기
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(event -> {
            try {
                Parent signInRoot = FXMLLoader.load(getClass().getResource("/gui/SignInAndUp.fxml"));
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(new Scene(signInRoot, 600, 450));
                stage.setTitle("Sign In and Up");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        delay.play(); // 타이머 시작
    }
}
