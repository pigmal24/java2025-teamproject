package gui;

import handle2.Task;
import handle2.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import respository.TaskRepository;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;

public class ModifyTaskController {
	
	@FXML private TextField subjectField;
	@FXML private TextField titleField;
	@FXML private TextField deadlineField;
	
	private Task originalTask;
	private User loggedInUser;
	
	public void setUser(User user) {
		this.loggedInUser = user;
	}
	
	public void setTask(Task task) {
		this.originalTask = task;
		
		subjectField.setText(task.getSubject());
		titleField.setText(task.getTitle());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		deadlineField.setPromptText(task.getDeadline().format(formatter));
	}
	
	@FXML
	public void handleUpdate() {
		String newSubject = subjectField.getText();
		String newTitle = titleField.getText();
		String newDeadlineString = deadlineField.getText();
		
		boolean updated = false;
		
		TaskRepository repo = new TaskRepository();
		
		if (!newSubject.equals(originalTask.getSubject())) {
			originalTask = repo.updateSubject(originalTask.getTaskId(), newSubject);
			updated = true;
		}
		
		if (!newTitle.equals(originalTask.getTitle())) {
			originalTask = repo.updateTitle(originalTask.getTaskId(), newTitle);
			updated = true;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		try {
			String oldDeadlineString = originalTask.getDeadline().format(formatter);
			if (!newDeadlineString.equals(oldDeadlineString)) {
				originalTask = repo.updateDeadLine(originalTask.getTaskId(), newDeadlineString);
				updated = true;
			}
		} catch (Exception e) {
			javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
			alert.setTitle("잘못된 날짜 형식");
			alert.setHeaderText("마감기한 입력 오류");
			alert.setContentText("마감기한은 'yyyy-MM-dd HH:mm' 형식으로 입력해야 합니다.\n예: 2025-06-10 23:59");
			alert.showAndWait();
			return;
		}

		if (updated) {
			System.out.println("Task successfully updated.");
		}
		
		else {
			System.out.println("No changes detected.");
		}
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
			Parent root = loader.load();
			
			HomeController controller = loader.getController();
			controller.setUser(loggedInUser);
			
			Stage stage = (Stage) subjectField.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("Home");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
    public void handleLogoClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
            Parent root = loader.load();
            
            HomeController homeController = loader.getController();
            if (loggedInUser != null) {
                homeController.setUser(loggedInUser);
            }
            
            Stage stage = (Stage) subjectField.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
