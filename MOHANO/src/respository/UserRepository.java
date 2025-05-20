package respository;

import connection.DBConnectionUtil;
import domain.User;

import java.sql.*;
import java.util.*;

import static connection.ConnectionConst.*;
import static connection.DBConnectionUtil.*;
import static java.sql.DriverManager.*;

public class  UserRepository {



    // 사용자의 id, password 를 저장할 변수 store
    private static Map<Integer, User> store = new HashMap<>();

    // User 테이블의 기본 id 를 저장할 변수 sequence
    private static int sequence = 0;

    private static final UserRepository instance = new UserRepository();


    public static UserRepository getInstance() {
        return instance;
    }

    // 외부에서 UserRepository 생성 불가
    // UserRepository 는 로직 실행 동안 1개만 생성(싱글톤)
    private UserRepository() {
        store = findAll();
    }
    public void save(User user) {

        String sql = "insert into user(id,schoolNum,emailAddress,smtpPass,studentName) " +
                "values(?,?,?,?,?)";

        Connection con = null;
        PreparedStatement pstmt=  null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, sequence++);
            pstmt.setString(2, user.getSchoolNum());
            pstmt.setString(3,user.getEmailAddress());
            pstmt.setString(4, user.getSmtpPass());
            pstmt.setString(5, user.getStudentName());
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // id(Pk)를 통해 해당 객체를 반환하는 메서드
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setSchoolNum(rs.getString("schoolNum"));
                    user.setEmailAddress(rs.getString("emailAddress"));
                    user.setSmtpPass(rs.getString("smtpPass"));
                    user.setStudentName(rs.getString("studentName"));
                    return user;
                } else {
                    return null;  // 해당 id가 없으면 null 반환
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // LoginId(SchoolNum) 을 통해 해당 객체를 반환하는 메서드
    public Optional<User> findByLoginId(String schoolNum) {
        
        // Map 에 담긴 values 값들만 담은 ArrayList 생성
        ArrayList<User> users = new ArrayList<>(store.values());
        
        // 아이디 schoolNum 에 해당하는 객체가 있을 경우 데이터 반환
        // 없을 경우 NULL 반환
        return users.stream().filter(m -> m.getSchoolNum().equals(schoolNum)).findFirst();
    }

    // 모든 User 정보를 담는 메서드
    public Map<Integer, User> findAll() {
        Map<Integer, User> store = new HashMap<>();

        String sql = "SELECT * FROM users";

        try {

            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                int id = rs.getInt("id");

                user.setId(id);
                user.setSchoolNum(rs.getString("schoolNum"));
                user.setEmailAddress(rs.getString("emailAddress"));
                user.setSmtpPass(rs.getString("smtpPass"));
                user.setStudentName(rs.getString("studentName"));

                store.put(id, user);
            }
            return store;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
