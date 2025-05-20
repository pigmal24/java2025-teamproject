package respository;

import connection.DBConnectionUtil;
import domain.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static connection.DBConnectionUtil.*;

public class TaskRepository {

    public void save(Task task) {

        String sql = "insert into task(taskId,userId,taskSub,taskName,deadLine) " +
                "values(?,?,?,?,?)";

        Connection con = null;
        PreparedStatement pstmt=  null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, task.getTaskId());
            pstmt.setInt(2, task.getUserId());
            pstmt.setString(3, task.getTaskSub());
            pstmt.setString(4, task.getTaskName());
            pstmt.setString(5, task.getDeadLine());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}