package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static connection.ConnectionConst.*;


// 데이터 연결 메서드
// URL, USERNAME, PASSWORD 를 이용해 mariaDB 에 연결
public class DBConnectionUtil {

    public static Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw  new IllegalArgumentException(e);
        }
    }
}
