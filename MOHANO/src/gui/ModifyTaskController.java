package gui;

import handle2.Task;
import javafx.fxml.FXML;
import respository.TaskRepository;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ModifyTaskController {
	
	@FXML private TextField subjectField;
	@FXML private TextField titleField;
	@FXML private TextField deadlineField;
	
	private Task originalTask;
	
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
		
		if (!newSubject.equals(originalTask.getSubject())) {
			TaskRepository repo = new TaskRepository();
			originalTask = repo.updateSubject(originalTask.getTaskId(), newSubject);
			updated = true;
		}
		
		if (!newTitle.equals(originalTask.getTitle())) {
			TaskRepository repo = new TaskRepository();
			originalTask = repo.updateTitle(originalTask.getTaskId(), newTitle);
			updated = true;
		}
		
		String oldDeadlineString = originalTask.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		if (!newDeadlineString.equals(oldDeadlineString)) {
			TaskRepository repo = new TaskRepository();
			originalTask = repo.updateDeadLine(originalTask.getTaskId(), newDeadlineString);
			updated = true;
		}

		if (updated) {
			System.out.println("Task successfully updated.");
		}
		
		else {
			System.out.println("No changes detected.");
		}
		((Stage) subjectField.getScene().getWindow()).close();
	}
}
