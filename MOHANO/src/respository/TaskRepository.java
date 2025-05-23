    package respository;


    import handle2.Task;
    import handle2.User;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;

    import static connection.DBConnectionUtil.*;

    public class TaskRepository {

        private int sequence; // task 테이블의 기본 키(id)

        // TaskRepository 를 여러 번 생성하는 것을 막기 위해 싱글톤으로 생성
        /*
        싱글톤: 하나의 객체를 여러 번 생성하지 않고 프로그램이 끝날 때까지 공유
        */
        private static final TaskRepository instance = new TaskRepository();

        public static TaskRepository getInstance() {
            return instance;
        }

        public TaskRepository() {

            String sql = "SELECT COUNT(*) FROM task"; // user 테이블의 전체 행 수 세기
            Connection con = null;
            PreparedStatement pstmt=  null;
            try {
                con = getConnection();
                pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                // count 변수는 id를 구하기 위해 필요
                // 행의 개수를 id 에 sequence 에 저장
                if(rs.next()) {
                    sequence = rs.getInt(1) + 1;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void save(Task task, User user) {

            String sql = "insert into task(taskId,userId,subject,title,deadLine) " +
                    "values(?,?,?,?,?)";

            Connection con = null;
            PreparedStatement pstmt=  null;

            // taskID 를 설정
            // 추후 출력을 위해
            task.setTaskId(sequence);
            try {
                con = getConnection();
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, sequence++);
                pstmt.setInt(2, user.getId());
                pstmt.setString(3, task.getSubject());
                pstmt.setString(4, task.getTitle());

                // LocalTime 객체를 String 객체로 전환
                String deadLine = task.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                pstmt.setString(5, deadLine);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Task 테이블의 모든 데이터를 반환하는 메서드
        public List<Task> findAll() {

            String sql = "SELECT * FROM task";
            List<Task> tasks = new ArrayList<>();

            try {
                Connection con = getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    Task task = new Task();
                    task.setTaskId(rs.getInt("taskId"));
                    task.setUserId(rs.getInt("userId"));
                    task.setSubject(rs.getString("subject"));
                    task.setTitle(rs.getString("title"));
                    task.setDeadline(rs.getString("deadLine"));
                    tasks.add(task);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return tasks;
        }

        // User id 에 맞는 모든 과제를 반환
        public List<Task> findByUserIdTaskAll(User user) {

            List<Task> tasks = new ArrayList<>();
            String sql = " SELECT * " +
                    "FROM task, user " +
                    "WHERE task.userId = user.id and user.id = ?";


            try {
                Connection con = getConnection();
                PreparedStatement pstmt=  con.prepareStatement(sql);
                pstmt.setInt(1, user.getId());
                ResultSet rs = pstmt.executeQuery();


                while(rs.next()) {
                    Task task = new Task();
                    task.setTaskId(rs.getInt("taskId"));
                    task.setUserId(rs.getInt("userId"));
                    task.setSubject(rs.getString("subject"));
                    task.setTitle(rs.getString("title"));
                    task.setDeadline(rs.getString("deadLine"));

                    // tasks ArrayList 에 데이터베이스에서 읽은 데이터를 추가
                    tasks.add(task);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return tasks;
        }

        public Task removeById(int taskId) {

            String findSql = "SELECT * FROM task WHERE taskId = ?";
            String deleteSql = "DELETE FROM task " +
                    "WHERE taskId = ?";
            Task task = new Task(); // 삭제할 task 를 result 에 저장
            try {
                Connection con = getConnection();
                PreparedStatement findStmt = con.prepareStatement(findSql);
                findStmt.setInt(1, taskId);

                PreparedStatement deletePstmt=  con.prepareStatement(deleteSql);
                deletePstmt.setInt(1, taskId);
                ResultSet rs = findStmt.executeQuery();
                // taskId 에 해당하는 데이터가 있을 경우
                if(rs.next()) {
                    task.setTaskId(rs.getInt("taskId"));
                    task.setUserId(rs.getInt("userId"));
                    task.setSubject(rs.getString("subject"));
                    task.setTitle(rs.getString("title"));
                    task.setDeadline(rs.getString("deadLine"));
                    deletePstmt.executeUpdate(); // 삭제 진행
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return task;
        }

        // Subject 업데이트 메서드
        public Task updateSubject(int taskId, String changeSubject) {

            String findSql = "SELECT * from task WHERE taskId = ?";
            String updateSql = "UPDATE task " +
                    "SET subject = ? " +
                    "WHERE taskId = ?";

            // 반환할 객체 updateTask
            Task updateTask = new Task();
            try {
                Connection con = getConnection();
                PreparedStatement updatePstmt = con.prepareStatement(updateSql);
                updatePstmt.setString(1, changeSubject);
                updatePstmt.setInt(2, taskId);
                updatePstmt.executeUpdate();

                PreparedStatement findPstmt = con.prepareStatement(findSql);
                findPstmt.setInt(1, taskId);
                ResultSet rs = findPstmt.executeQuery();
                if(rs.next()) {
                    updateTask.setTaskId(rs.getInt("taskId"));
                    updateTask.setUserId(rs.getInt("userId"));
                    updateTask.setSubject(rs.getString("subject"));
                    updateTask.setTitle(rs.getString("title"));
                    updateTask.setDeadline(rs.getString("deadLine"));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return updateTask;
        }


        // Title 업데이트 메서드
        public Task updateTitle(int taskId, String changeTitle) {

            String findSql = "SELECT * from task WHERE taskId = ?";
            String updateSql = "UPDATE task " +
                    "SET title = ? " +
                    "WHERE taskId = ?";

            // 반환할 객체 updateTask
            Task updateTask = new Task();
            try {
                Connection con = getConnection();
                PreparedStatement updatePstmt = con.prepareStatement(updateSql);
                updatePstmt.setString(1, changeTitle);
                updatePstmt.setInt(2, taskId);
                updatePstmt.executeUpdate();

                PreparedStatement findPstmt = con.prepareStatement(findSql);
                findPstmt.setInt(1, taskId);
                ResultSet rs = findPstmt.executeQuery();
                if(rs.next()) {
                    updateTask.setTaskId(rs.getInt("taskId"));
                    updateTask.setUserId(rs.getInt("userId"));
                    updateTask.setSubject(rs.getString("subject"));
                    updateTask.setTitle(rs.getString("title"));
                    updateTask.setDeadline(rs.getString("deadLine"));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return updateTask;
        }

        // DeadLine 업데이트 메서드
        public Task updateDeadLine(int taskId, String changeDeadLine) {

            String findSql = "SELECT * from task WHERE taskId = ?";
            String updateSql = "UPDATE task " +
                    "SET deadLine = ? " +
                    "WHERE taskId = ?";

            // 반환할 객체 updateTask
            Task updateTask = new Task();
            try {
                Connection con = getConnection();
                PreparedStatement updatePstmt = con.prepareStatement(updateSql);
                updatePstmt.setString(1, changeDeadLine);
                updatePstmt.setInt(2, taskId);
                updatePstmt.executeUpdate();

                PreparedStatement findPstmt = con.prepareStatement(findSql);
                findPstmt.setInt(1, taskId);
                ResultSet rs = findPstmt.executeQuery();
                if(rs.next()) {
                    updateTask.setTaskId(rs.getInt("taskId"));
                    updateTask.setUserId(rs.getInt("userId"));
                    updateTask.setSubject(rs.getString("subject"));
                    updateTask.setTitle(rs.getString("title"));
                    updateTask.setDeadline(rs.getString("deadLine"));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return updateTask;
        }
    }