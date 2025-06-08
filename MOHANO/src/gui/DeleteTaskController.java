package gui;

import handle2.Task;
import handle2.User;
import respository.TaskRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
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

    private User loggedInUser;

    public void setUser(User user) {
        this.loggedInUser = user;
        loadTaskRows();
    }

    public void loadTaskRows() {
        List<Task> tasks = TaskRepository.getInstance().findByUserIdTaskAll(loggedInUser);
        ObservableList<TaskRow> taskRows = FXCollections.observableArrayList();

        for (Task task : tasks) {
            taskRows.add(new TaskRow(task));
        }

        taskTable.setItems(taskRows);
        
        // ★ 중요: SINGLE 모드로 설정해야 행 선택이 동작함
        taskTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // TableColumn 설정
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().deadlineProperty());
        subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    }

    @FXML
    public void handleDelete() {
        TaskRow selectedRow = taskTable.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            TaskRepository.getInstance().removeById(selectedRow.getTask().getTaskId());
            loadTaskRows();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("경고");
            alert.setHeaderText(null);
            alert.setContentText("삭제할 과제를 선택하세요.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/SignInAndUp.fxml"));
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogoClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            homeController.setUser(loggedInUser);

            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
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
    }
}
