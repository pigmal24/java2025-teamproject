package respository;

import handle2.User;

import java.sql.*;
import java.util.*;

import static connection.DBConnectionUtil.*;

public class  UserRepository {


    // User 테이블의 기본 id 를 저장할 변수 sequence
    private int sequence;


    // UserRepository 를 여러 번 생성하는 것을 막기 위해 싱글톤으로 생성
    /*
        싱글톤: 하나의 객체를 여러 번 생성하지 않고 프로그램이 끝날 때까지 공유
     */
    private static final UserRepository instance = new UserRepository();

    public static UserRepository getInstance() {
        return instance;
    }

    // 외부에서 UserRepository 생성 불가
    // UserRepository 는 로직 실행 동안 1개만 생성(싱글톤)
    private UserRepository() {

        String sql = "SELECT COUNT(*) FROM user"; // user 테이블의 전체 행 수 세기
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

    // 회원가입 메서드
    public void save(User user) {

        String sql = "insert into user(id,schoolNum,emailAddress,smtpPass,studentName, lmsId, lmsPass) " +
                "values(?,?,?,?,?,?,?)";

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
            pstmt.setString(6, user.getLmsId());
            pstmt.setString(7, user.getLmsPass());
            pstmt.executeUpdate();
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

        
        // 아이디 schoolNum 에 해당하는 객체가 있을 경우 데이터 반환
        // 없을 경우 NULL 반환
        return findAll().stream().filter(m -> m.getSchoolNum().equals(schoolNum)).findFirst();
    }
    
    // 모든 User의 정보를 담는 메서드
    // ArrayList 로 반환
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

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
                user.setLmsId(rs.getString("lmsId"));
                user.setLmsPass(rs.getString("lmsPass"));
                users.add(user);
            }
            System.out.println("---users 반환 성공---");
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // User 학번(schoolNum) 변경 메서드
    public User updateSchoolNum(User user, String updateSchoolNum) {
    	
    	 String sql = "UPDATE user SET schoolNum = ? WHERE id = ?";
    	 User updateUser = new User();
    	 try {
    		 Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
          
             System.out.println("updateSchoolNum: " + updateSchoolNum);
             System.out.println("userId: " + user.getId());
             pstmt.setString(1, updateSchoolNum);
             pstmt.setInt(2,  user.getId());
             
             pstmt.executeUpdate();
             user.setSchoolNum(updateSchoolNum);
    	 }  catch (SQLException e) {
             throw new RuntimeException(e);
         }
    	 return updateUser;
    }
    
    // User 이메일(emailAddress) 변경 메서드
    public User updateEmailAddress(User user, String updateEmailAddress) {
    	
    	 String sql = "UPDATE user SET emailAddress = ? WHERE id = ?";
    	 User updateUser = new User();
    	 try {
    		 Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             
             pstmt.setString(1, updateEmailAddress);
             pstmt.setInt(2,  user.getId());
             
             pstmt.executeUpdate();
             user.setEmailAddress(updateEmailAddress);
    	 }  catch (SQLException e) {
             throw new RuntimeException(e);
         }
    	 return updateUser;
    }
    
    // User smtpPass 변경 메서드
    public User updateSmtpPass(User user, String updateSmtpPass) {
    	
    	 String sql = "UPDATE user SET smtpPass = ? WHERE id = ?";
    	 User updateUser = new User();
    	 try {
    		 Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             
             pstmt.setString(1, updateSmtpPass);
             pstmt.setInt(2,  user.getId());
             
             pstmt.executeUpdate();
             user.setSmtpPass(updateSmtpPass);
    	 }  catch (SQLException e) {
             throw new RuntimeException(e);
         }
    	 return updateUser;
    }
    
    // User 이름(studentName) 변경 메서드
    
    public User updateStudentName(User user, String updateStudentName) {
    	
    	 String sql = "UPDATE user SET studentName = ? WHERE id = ?";
    	 User updateUser = new User();
    	 try {
    		 Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             
             pstmt.setString(1, updateStudentName);
             pstmt.setInt(2,  user.getId());
             
             pstmt.executeUpdate();
             user.setStudentName(updateStudentName);
    	 }  catch (SQLException e) {
             throw new RuntimeException(e);
         }
    	 return updateUser;
    }
    
 // User lmsId 변경 메서드
    public User updateLmsId(User user, String updateLmsId) {
        String sql = "UPDATE user SET lmsId = ? WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, updateLmsId);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();
            user.setLmsId(updateLmsId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    // User lmsPass 변경 메서드
    public User updateLmsPass(User user, String updateLmsPass) {
        String sql = "UPDATE user SET lmsPass = ? WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, updateLmsPass);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();
            user.setLmsPass(updateLmsPass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

}
