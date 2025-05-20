package test;

import connection.DBConnectionUtil;
import domain.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static connection.DBConnectionUtil.*;

/*
    Task 테이블 정상 작동 Test
 */
public class TaskTest {

    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select * from task";

        try {

            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Task task = new Task();
                task.setTaskId(rs.getInt("taskId"));
                task.setUserId(rs.getInt("userId"));
                task.setTaskSub(rs.getString("taskSub"));
                task.setTaskName(rs.getString("taskName"));
                task.setDeadLine(rs.getString("deadLine"));

                System.out.println(task);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
