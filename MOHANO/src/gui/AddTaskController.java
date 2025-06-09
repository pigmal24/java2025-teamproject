package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import respository.TaskRepository;
import handle2.Task;
import handle2.User;

import java.util.List;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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
           String subject = subjectField.getText().trim();
           String title = titleField.getText().trim();
           String deadlineString = deadlineField.getText().trim();

           if (subject.isEmpty() || title.isEmpty() || deadlineString.isEmpty()) {
        	   showAlert("모든 입력란을 채워주세요.");
        	   return;
           }
           
           // 날짜 형식이 "yyyy-MM-dd"로만 입력되면 시간 추가
           if (deadlineString.length() == 10) {
               deadlineString += " 00:00";
           }

           Task newTask = new Task();
           newTask.setSubject(subject);
           newTask.setTitle(title);
           
           try {
        	   newTask.setDeadline(deadlineString);
           } catch (DateTimeParseException e) {
        	   showAlert("날짜 형식이 올바르지 않습니다.\n예시: 2025-06-25 23:59");
        	   return;
           }
           
           // DB에 새 과제 저장
           TaskRepository.getInstance().save(newTask, loggedInUser);

           // 저장 후 최신 과제 목록 DB에서 다시 조회
           List<Task> updatedTasks = TaskRepository.getInstance().findByUserIdTaskAll(loggedInUser);
           List<Task> personalTasks = updatedTasks.stream().filter(task -> !task.getSubject().startsWith("LMS_")).toList();
           
           // Home 화면 FXML 로드 및 컨트롤러 접근
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
           Parent root = loader.load();

           HomeController homeController = loader.getController();
           homeController.setUser(loggedInUser);
           homeController.setPersonalTasks(personalTasks);  // 최신 과제 목록 갱신

           Stage stage = (Stage) subjectField.getScene().getWindow();
           stage.setScene(new Scene(root, 600, 450));
           stage.setTitle("Home");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   private void showAlert(String msg) {
	   Alert alert = new Alert(Alert.AlertType.ERROR);
	   alert.setTitle("입력 오류");
	   alert.setHeaderText(null);
	   alert.setContentText(msg);
	   alert.showAndWait();
   }
   
   @FXML
   private void handleLogoClick() {
       try {
           // Home.fxml 로드
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
           Parent root = loader.load();

           // HomeController에 사용자 정보 전달
           HomeController homeController = loader.getController();
           if (loggedInUser != null) {
               homeController.setUser(loggedInUser);  // setUser가 모든 과제 처리 포함
           }

           // 현재 창 전환
           Stage stage = (Stage) subjectField.getScene().getWindow();
           stage.setScene(new Scene(root, 600, 450));
           stage.setTitle("Home");
           stage.show();  // 명시적으로 show() 호출
       } catch (Exception e) {
           e.printStackTrace();
           showAlert("화면 전환 중 오류가 발생했습니다.");
       }
   }

}
