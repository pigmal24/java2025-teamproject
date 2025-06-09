package console;

import static connection.MakeTable.*;

import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MOHANOMain extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SignInAndUp.fxml"));
	    Parent root = loader.load();
	    Scene scene = new Scene(root);

	    // ✅ 이 줄을 꼭 추가하세요!
	    scene.getStylesheets().add(getClass().getResource("/gui/style.css").toExternalForm());

	    primaryStage.setTitle("Sign In and Up");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}


   public static void main(String[] args) {
	  
	  checkTable();
      launch(args);
   }
}