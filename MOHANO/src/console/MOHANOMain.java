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
   public void start(Stage primaryStage) {
      try {
         Parent root = FXMLLoader.load(getClass().getResource("/gui/StartView.fxml"));
         primaryStage.setTitle("MOHANO 로그인");
         primaryStage.setScene(new Scene(root, 600, 450));
         primaryStage.show();	
      } catch(Exception e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
	  
	  checkTable();
      launch(args);
   }
}