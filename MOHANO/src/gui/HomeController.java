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
import handle2.User;
import handle2.EmailSender;

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

    // 로그인한 사용자와 과제 리스트 저장용 필드
    private User currentUser;
    private List<Task> currentTasks;

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

    // LMS 과제 리스트를 TableView에 세팅하고 필드에 저장
    public void setTasks(List<Task> tasks) {
        this.currentTasks = tasks;
        if (tasks == null) {
            lmsTaskTable.setItems(FXCollections.observableArrayList());
            return;
        }
        lmsTaskTable.setItems(FXCollections.observableArrayList(tasks));
    }

    // 24시간 내 과제 리스트를 TableView에 세팅 (필요시)
    public void setUrgentTasks(List<Task> urgentTasks) {
        urgentTaskTable.setItems(FXCollections.observableArrayList(urgentTasks));
    }

    // User 객체 세팅
    public void setUser(User user) {
        this.currentUser = user;
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
        // 로그아웃 시 이메일 전송
        try {
            if (currentUser != null && currentTasks != null) {
                // 오늘 마감 과제와 마감 지난 과제 분류
                List<Task> todayTask = currentTasks.stream()
                    .filter(task -> {
                        var now = java.time.LocalDateTime.now();
                        var deadline = task.getDeadline();
                        return !deadline.isBefore(now) && deadline.isBefore(now.plusDays(1));
                    })
                    .toList();

                List<Task> removedTask = currentTasks.stream()
                    .filter(task -> task.getDeadline().isBefore(java.time.LocalDateTime.now()))
                    .toList();

                EmailSender emailSender = new EmailSender();
                emailSender.sendUserTaskEmail(currentUser, todayTask, removedTask);
            }
        } catch (Exception e) {
            System.out.println("로그아웃 이메일 전송 실패: " + e.getMessage());
        }

        // 기존 로그아웃 화면 전환
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AddTask.fxml"));
            Parent root = loader.load();

            AddTaskController controller = loader.getController();
            controller.setUser(this.currentUser);  // 로그인한 유저 정보 전달!

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
