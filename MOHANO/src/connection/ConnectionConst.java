package connection;


// mariaDB 에 연결하기 위한 정보들을 상수로 저장
public  abstract class ConnectionConst {

    public static final String URL = "jdbc:mariadb://localhost:3308/java_db?useUnicode=true&characterEncoding=utf8mb4&connectionCollation=utf8mb4_general_ci";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "1234";
}
