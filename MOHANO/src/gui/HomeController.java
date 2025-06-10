package gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import respository.TaskRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import handle2.Task;
import handle2.User;
import handle2.EmailSender;

import javafx.beans.property.SimpleStringProperty;

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

    // 과제 목록 테이블
    @FXML private TableView<Task> personalTaskTable;
    @FXML private TableColumn<Task, String> personalSubjectCol;
    @FXML private TableColumn<Task, String> personalTitleCol;
    @FXML private TableColumn<Task, String> personalDeadlineCol;
    
    // 로그인한 사용자와 과제 리스트 저장용 필드
    private User loggedInUser;
    private List<Task> currentTasks;
    private List<Task> lmsTasks = new ArrayList<>();

//    @FXML
//    public void initialize() {
//        System.out.println("🔧 initialize() 진입");
//        System.out.println("📋 subjectCol is null? " + (subjectCol == null));
//        System.out.println("📋 titleCol is null? " + (titleCol == null));
//        System.out.println("📋 deadlineCol is null? " + (deadlineCol == null));
//
//        System.out.println("🔧 initialize() 진입");
//
//        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
//        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
//
//        deadlineCol.setCellValueFactory(cellData -> {
//            String value = cellData.getValue().getDeadlineString();
//            System.out.println("[LMS 마감일 셀 값]: " + value);
//            return new SimpleStringProperty(value);
//        });
//
//        // personalTaskTable도 동일하게 바인딩
//        personalSubjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
//        personalTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
//        personalDeadlineCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeadlineString()));
//
//        // 더블클릭 시 수정 페이지 이동
//        personalTaskTable.setRowFactory(tv -> {
//            TableRow<Task> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (!row.isEmpty() && event.getClickCount() == 2) {
//                    Task clickedTask = row.getItem();
//                    event.consume();
//                    Platform.runLater(() -> openModifyTaskPage(clickedTask));
//                }
//            });
//            return row;
//        });
//        
//
//    }
    @FXML
    public void initialize() {
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        deadlineCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDeadlineString()));

        personalSubjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        personalTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        personalDeadlineCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getDeadlineString()));
        
        //더블클릭 시 수정 페이지 이동
        personalTaskTable.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Task clickedTask = row.getItem();
                    event.consume();
                    Platform.runLater(() -> openModifyTaskPage(clickedTask));
                }
            });
            return row;
        });
    }

    private void openModifyTaskPage(Task task) {
       try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ModifyTask.fxml"));
          Parent root = loader.load();
          
          ModifyTaskController controller = loader.getController();
          controller.setTask(task);
          controller.setUser(loggedInUser);
          
          Stage stage = (Stage) personalTaskTable.getScene().getWindow();
          stage.setScene(new Scene(root, 450, 350));
          stage.setTitle("Modify Task");
       } catch (Exception e) {
          e.printStackTrace();
       }
    }
    // LMS 과제 리스트를 TableView에 세팅하고 필드에 저장
    public void setTasks(List<Task> tasks) {
        this.currentTasks = tasks;
        if (tasks == null) {
            lmsTaskTable.setItems(FXCollections.observableArrayList());
            return;
        }
        
        List<Task> lmsOnly = tasks.stream().filter(task -> task.getSubject().startsWith("LMS_")).toList();
        lmsTaskTable.setItems(FXCollections.observableArrayList(lmsOnly));
    }

    // User 객체 세팅
    public void setUser(User user) {
        this.loggedInUser = user;

        TaskRepository.getInstance().removePastTasksAll(user);
        List<Task> allTasks = TaskRepository.getInstance().findByUserIdTaskAll(user);

        applyTaskListWithDedup(allTasks); // 중복 제거 및 분리 세팅
    }

    public void loadWithTasks(User user, List<Task> allTasks) {
        this.loggedInUser = user;

        Set<String> seen = new HashSet<>();
        this.lmsTasks = allTasks.stream()
            .filter(Task::isLmsTask)
            .filter(task -> seen.add(task.getTitle() + task.getDeadline() + task.getSubject()))
            .toList();

        seen.clear();
        this.currentTasks = allTasks.stream()
            .filter(task -> !task.isLmsTask())
            .filter(task -> seen.add(task.getTitle() + task.getDeadline() + task.getSubject()))
            .toList();

        lmsTaskTable.setItems(FXCollections.observableArrayList(this.lmsTasks));
        personalTaskTable.setItems(FXCollections.observableArrayList(this.currentTasks));
    }

    private void applyTaskListWithDedup(List<Task> allTasks) {
        Set<String> seen = new HashSet<>();

        this.lmsTasks = allTasks.stream()
            .filter(Task::isLmsTask)
            .filter(task -> seen.add(task.getTitle() + task.getDeadline() + task.getSubject()))
            .toList();

        seen.clear();

        this.currentTasks = allTasks.stream()
            .filter(task -> !task.isLmsTask())
            .filter(task -> seen.add(task.getTitle() + task.getDeadline() + task.getSubject()))
            .toList();

        lmsTaskTable.setItems(FXCollections.observableArrayList(this.lmsTasks));
        personalTaskTable.setItems(FXCollections.observableArrayList(this.currentTasks));
    }

    public void refreshOnlyPersonalTasks() {
        if (this.loggedInUser == null) return;

        List<Task> allTasks = TaskRepository.getInstance().findByUserIdTaskAll(loggedInUser);

        Set<String> seen = new HashSet<>();
        this.currentTasks = allTasks.stream()
            .filter(task -> !task.isLmsTask())
            .filter(task -> seen.add(task.getTitle() + task.getDeadline() + task.getSubject()))
            .toList();

        personalTaskTable.setItems(FXCollections.observableArrayList(this.currentTasks));
    }
    
    public void setPersonalTasks(List<Task> personalTasks) {
       this.currentTasks = personalTasks;
       refreshTasks();
    }
    
    public List<Task> getCurrentTasks() {
       return currentTasks;
    }
    
    public void refreshTasks() {
       if (this.loggedInUser == null) {
          return;
       }
       
       List<Task> allTasks = TaskRepository.getInstance().findByUserIdTaskAll(loggedInUser);
       List<Task> personalTasks = allTasks.stream().filter(task -> !task.getTitle().startsWith("LMS_")).toList();
       this.currentTasks = personalTasks;
       
       personalTaskTable.setItems(FXCollections.observableArrayList(this.currentTasks));
       lmsTasks = TaskRepository.getInstance().loadLmsTasks();
       lmsTaskTable.setItems(FXCollections.observableArrayList(this.lmsTasks));
    }

    @FXML
    public void handleEditProfile() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/EditProfile.fxml"));
        	Parent root = loader.load();
        	
        	EditProfileController controller = loader.getController();
        	controller.setUser(loggedInUser);
        	
            Stage stage = (Stage) editProfileButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 450));
            stage.setTitle("Edit Profile");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout() {
        // 로그아웃 시 이메일 전송
        try {
            if (loggedInUser != null && currentTasks != null) {
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
                emailSender.sendUserTaskEmail(loggedInUser, todayTask, removedTask);
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
            controller.setUser(this.loggedInUser);  // 로그인한 유저 정보 전달

            Stage stage = (Stage) addTaskButton.getScene().getWindow();
            stage.setScene(new Scene(root, 450, 350));
            stage.setTitle("Add Task");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleDeleteTask() {
       try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/deleteTask.fxml"));
          Parent root = loader.load();
          
          DeleteTaskController controller = loader.getController();
          controller.setUser(loggedInUser);
          
          Stage stage = (Stage) deleteTaskButton.getScene().getWindow();
          stage.setScene(new Scene(root));
          stage.setTitle("Delete Task");
       } catch (Exception e) {
          e.printStackTrace();
       }
    }
}