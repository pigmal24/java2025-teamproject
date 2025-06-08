package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import respository.TaskRepository;
import handle2.Task;
import handle2.User;
import java.util.List;

public class AddTaskController {
   @FXML private TextField subjectField;
   @FXML private TextField titleField;
   @FXML private TextField deadlineField;

   private User loggedInUser;

   public void setUser(User user) {
      this.loggedInUser = user;
   }

   @FXML
   public void handleAdd() {
       try {
           String subject = subjectField.getText();
           String title = titleField.getText();
           String deadlineString = deadlineField.getText();

           // 날짜 형식이 "yyyy-MM-dd"로만 입력되면 시간 추가
           if (deadlineString.length() == 10) {
               deadlineString += " 00:00";
           }

           Task newTask = new Task();
           newTask.setSubject(subject);
           newTask.setTitle(title);
           newTask.setDeadline(deadlineString);

           // DB에 새 과제 저장
           TaskRepository.getInstance().save(newTask, loggedInUser);

           // 저장 후 최신 과제 목록 DB에서 다시 조회
           List<Task> updatedTasks = TaskRepository.getInstance().findByUserIdTaskAll(loggedInUser);

           // Home 화면 FXML 로드 및 컨트롤러 접근
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
           Parent root = loader.load();

           HomeController homeController = loader.getController();
           homeController.setUser(loggedInUser);
           homeController.setTasks(updatedTasks);  // 최신 과제 목록 갱신

           Stage stage = (Stage) subjectField.getScene().getWindow();
           stage.setScene(new Scene(root, 600, 450));
           stage.setTitle("Home");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   @FXML
   private void handleLogoClick() {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
           Parent root = loader.load();
           
           HomeController homeController = loader.getController();
           if (loggedInUser != null) {
               homeController.setUser(loggedInUser);
               List<Task> updatedTasks = TaskRepository.getInstance().findByUserIdTaskAll(loggedInUser);
               homeController.setTasks(updatedTasks);
           }
           
           Stage stage = (Stage) subjectField.getScene().getWindow();
           stage.setScene(new Scene(root, 600, 450));
           stage.setTitle("Home");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
