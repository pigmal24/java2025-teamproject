package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartViewController {

    @FXML
    private Pane rootPane;  // FXML에서 fx:id="rootPane"로 지정한 Pane

    @FXML
    public void initialize() {
        try {
            // SignInAndUp.fxml을 로드
            Parent signInRoot = FXMLLoader.load(getClass().getResource("/gui/SignInAndUp.fxml"));

            // 현재 Stage를 가져오기
            Stage stage = (Stage) rootPane.getScene().getWindow();

            // 새 Scene 세팅
            stage.setScene(new Scene(signInRoot, 600, 400));
            stage.setTitle("Sign In and Up");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
