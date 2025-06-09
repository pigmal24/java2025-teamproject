package gui;

import handle2.Task;
import handle2.User;
import respository.TaskRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DeleteTaskController {

    @FXML private TableView<TaskRow> taskTable;
    @FXML private TableColumn<TaskRow, String> deadlineColumn;
    @FXML private TableColumn<TaskRow, String> subjectColumn;
    @FXML private TableColumn<TaskRow, String> titleColumn;
    @FXML private TableColumn<TaskRow, Boolean> selectCol;
    
    private User loggedInUser;
    private ObservableList<TaskRow> taskRows = FXCollections.observableArrayList();
    
    public void setUser(User user) {
        this.loggedInUser = user;
        loadTaskRows();
    }

    public void loadTaskRows() {
        List<Task> tasks = TaskRepository.getInstance().findByUserIdTaskAll(loggedInUser);
        taskRows.clear();
        
        for (Task task : tasks) {
            taskRows.add(new TaskRow(task));
        }

        taskTable.setItems(taskRows);
        
        selectCol.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectCol.setCellFactory(tc -> new CheckBoxTableCell<>());

        taskTable.setEditable(true);
        selectCol.setEditable(true);
        
        selectCol.setCellFactory(tc -> {
        	CheckBoxTableCell<TaskRow, Boolean> cell = new CheckBoxTableCell<>();
        	cell.setEditable(true);
        	return cell;
        });
        
        // TableColumn 설정
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().deadlineProperty());
        subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    }
    
    @FXML
    public void handleDelete() {
        List<TaskRow> toDelete = taskRows.stream().filter(TaskRow::isSelected).toList();

        if (toDelete.isEmpty()) {
            showAlert("삭제할 과제를 선택하세요.");
            return;
        }
        
        for (TaskRow row : toDelete) {
        	TaskRepository.getInstance().removeById(row.getTask().getTaskId());
        }
        
        loadTaskRows();
    }
    
    private void showAlert(String msg) {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	alert.setTitle("notify");
    	alert.setHeaderText(null);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
    
    @FXML
    public void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignInAndUp.fxml"));
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
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

            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TableView에서 사용할 래퍼 클래스
    public static class TaskRow {
        private final Task task;
        private final SimpleStringProperty subject;
        private final SimpleStringProperty title;
        private final SimpleStringProperty deadline;
        private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
        
        public boolean isSelected() {
        	return selected.get();
        }
        
        public TaskRow(Task task) {
            this.task = task;
            this.subject = new SimpleStringProperty(task.getSubject());
            this.title = new SimpleStringProperty(task.getTitle());
            this.deadline = new SimpleStringProperty(task.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        public Task getTask() {
            return task;
        }
        public SimpleStringProperty subjectProperty() {
            return subject;
        }
        public SimpleStringProperty titleProperty() {
            return title;
        }
        public SimpleStringProperty deadlineProperty() {
            return deadline;
        }
        public SimpleBooleanProperty selectedProperty() {
        	return selected;
        }
    }
}
