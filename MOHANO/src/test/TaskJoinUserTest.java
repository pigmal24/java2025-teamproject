package test;

import domain.Task;
import domain.TaskJoinUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static connection.DBConnectionUtil.getConnection;

public class TaskJoinUserTest {

    public static void main(String[] args) {


        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT id,taskId,taskSub,taskName, deadLine,schoolNum, emailAddress, smtpPass, studentName " +
                "FROM task, users " +
                "WHERE task.userId = users.id";

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {

                TaskJoinUser taskJoinUser = new TaskJoinUser();
                taskJoinUser.setTaskId(rs.getInt("id"));
                taskJoinUser.setId(rs.getInt("taskId"));
                taskJoinUser.setTaskSub(rs.getString("taskSub"));
                taskJoinUser.setTaskName(rs.getString("taskName"));
                taskJoinUser.setDeadLine(rs.getString("deadLine"));
                taskJoinUser.setSchoolNum(rs.getString("schoolNum"));
                taskJoinUser.setEmailAddress(rs.getString("emailAddress"));
                taskJoinUser.setSmtpPass(rs.getString("smtpPass"));
                taskJoinUser.setStudentName(rs.getString("studentName"));
                System.out.println(taskJoinUser);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
