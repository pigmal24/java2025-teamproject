package test;

import domain.User;

import java.sql.*;

import static connection.ConnectionConst.*;


/*
    User 테이블 정상 작동 테스트
 */
public class UserTest {
    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select * from users";
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {

                User user1 = new User();
                user1.setId(rs.getInt("id"));
                user1.setSchoolNum(rs.getString("schoolNum"));
                user1.setEmailAddress(rs.getString("emailAddress"));
                user1.setSmtpPass(rs.getString("smtpPass"));
                user1.setStudentName(rs.getString("studentName"));

                System.out.println(user1);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}