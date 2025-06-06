package connection;

import static connection.DBConnectionUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

import respository.UserRepository;

// 테이블 생성 메서드
// 테이블이 있으면 생성 xx
// 테이블이 존재하지 않을 시 테이블 생성
public  class MakeTable {

	UserRepository userRepository = UserRepository.getInstance();

	// 테이블 생성 메서드
	// 테이블이 존재할 시 생성 xx
	// user 테이블 생성 후 task 테이블 생성
	public static void checkTable() {

		// 테이블 생성 전 스키마 유무 확인 메서드 실행
		//checkSchema();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rsUser = null;
		try {
			con = getConnection();
			DatabaseMetaData dbm = con.getMetaData();

			rsUser = dbm.getTables(null, null, "user", null);

			// 테이블이 존재하지 않을 경우 테이블 생성
			if (!rsUser.next()) {
				  String createUserTable = """
				            CREATE TABLE user (
				                id INT PRIMARY KEY,
				                schoolNum VARCHAR(20),
				                emailAddress VARCHAR(100),
				                smtpPass VARCHAR(100),
				                studentName VARCHAR(50),
				                lmsId VARCHAR(50),
				                lmsPass VARCHAR(50)
				            );
				        """;
				pstmt = con.prepareStatement(createUserTable);
				pstmt.executeUpdate();

			}
			// 테이블 존재 시
			else {
				//System.out.println("이미 user 테이블이 존재합니다");
			}
			
			// 2. task 테이블 유무 확인
			ResultSet rsTask = dbm.getTables(null, null, "TASK", null);
			 if (!rsTask.next()) {
	                String createTaskTable = """
	                    CREATE TABLE task (
	                        taskId INT PRIMARY KEY,
	                        userId INT,
	                        subject VARCHAR(100),
	                        title VARCHAR(255),
	                        deadline VARCHAR(100),
	                        FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE
	                    )
	                """;
	                pstmt = con.prepareStatement(createTaskTable);
					pstmt.executeUpdate();
			 }
			 
			 // Task 테이블 존재 시
			 else {
				 //System.out.println("Task 테이블이 이미 존재합니다.");
			 }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
