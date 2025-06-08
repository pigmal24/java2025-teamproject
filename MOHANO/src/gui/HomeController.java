package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;
import handle2.Task; 

public class HomeController {

    @FXML
    private Button editProfileButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button addTaskButton;

    @FXML
    private Button deleteTaskButton;

    // LMS 과제 테이블
    @FXML private TableView<Task> lmsTaskTable;
    @FXML private TableColumn<Task, String> subjectCol;
    @FXML private TableColumn<Task, String> titleCol;
    @FXML private TableColumn<Task, String> deadlineCol;

    // 24시간 내 과제 테이블
    @FXML private TableView<Task> urgentTaskTable;
    @FXML private TableColumn<Task, String> urgentSubjectCol;
    @FXML private TableColumn<Task, String> urgentTitleCol;
    @FXML private TableColumn<Task, String> urgentDeadlineCol;

    @FXML
    public void initialize() {
        // LMS 과제 TableView 컬럼 설정
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        // 24시간 내 과제 TableView 컬럼 설정
        urgentSubjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        urgentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        urgentDeadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));
    }

    // LMS 과제 리스트를 TableView에 세팅
    public void setTasks(List<Task> tasks) {
    	System.out.println(tasks);
        lmsTaskTable.setItems(FXCollections.observableArrayList(tasks));
    }

    // 24시간 내 과제 리스트를 TableView에 세팅 (필요시)
    public void setUrgentTasks(List<Task> urgentTasks) {
        urgentTaskTable.setItems(FXCollections.observableArrayList(urgentTasks));
    }
    
    

    @FXML
    public void handleEditProfile() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/EditProfile.fxml"));
            Stage stage = (Stage) editProfileButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Edit Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/SignInAndUp.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Sign In & Up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddTask() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/AddTask.fxml"));
            Stage stage = (Stage) addTaskButton.getScene().getWindow();
            stage.setScene(new Scene(root, 450, 400));
            stage.setTitle("Add Task");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteTask() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/deleteTask.fxml"));
            Stage stage = (Stage) deleteTaskButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Delete Task");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
