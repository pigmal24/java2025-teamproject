package console;

import static connection.MakeTable.*;

import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MOHANOMain extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/StartView.fxml"));
	    Parent root = loader.load();
	    Scene scene = new Scene(root);

	    scene.getStylesheets().add(getClass().getResource("/gui/style.css").toExternalForm());
	    
	    Font.loadFont(getClass().getResourceAsStream("/Pretendard-Regular.ttf"), 12);
	    Font.loadFont(getClass().getResourceAsStream("/Pretendard-Bold.ttf"), 12);
	    Font.loadFont(getClass().getResourceAsStream("/PyeongChangPeace-Bold.ttf"), 12);
	    
	    primaryStage.setTitle("Sign In and Up");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}


   public static void main(String[] args) {
	  
	  checkTable();
      launch(args);
   }
}