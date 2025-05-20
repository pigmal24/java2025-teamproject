/**
 * 
 */
package withoutDB;
import java.util.*;
/**
 * @Author : 2023007915 김준혁
 * @Project : 2025 자바 팀프로젝트
 * @프로그램_설명 : 실행 초기 메뉴, 로그인 메뉴, 회원가입 메뉴 구현
 * @File_Name : menu.java
 */
public class SettingMenu {
	private ArrayList<StudentTask> studentList;
	private Scanner sc;
	
	public SettingMenu(Scanner sc) {
		this.sc = sc;
		studentList = new ArrayList<>();
	}
	
    public static void clearConsole() {
        // ANSI escape code로 화면을 지움
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void del1s() {
        try {
            Thread.sleep(1000); // 딜레이 1초
        } catch (InterruptedException e) {
            e.printStackTrace(); // 또는 무시해도 OK
        }
    }
    public static void del3s() {
        try {
            Thread.sleep(3000); // 딜레이 3초
        } catch (InterruptedException e) {
            e.printStackTrace(); // 또는 무시해도 OK
        }
    }
    
 // 학번 중복 체크
    public boolean isStudentIdTaken(String studentId) {
        for (StudentTask s : studentList) {
            if (s.getStudentId().equals(studentId)) {
                return true;
            }
        }
        return false;
    }
    
	public void setMenu() {
		int choice = 0;
		while(true) {
			clearConsole();
			System.out.printf("MOHANO - 과제 관리 시스템\n");
			System.out.printf("1. 로그인\n");
			System.out.printf("2. 회원가입\n");
			System.out.printf("3. MOHANO 종료\n");
			System.out.printf("2025-05-20 Author: wnsgur\n");
			System.out.printf(">> ");
			choice = Integer.parseInt(sc.nextLine()); 
			del1s();
			switch (choice) {
            case 1 : 
            	logInMenu();
            	break;
            case 2 : 
            	signUpMenu();
            	break;
            case 3 : 
                System.out.println("MOHANO를 종료합니다.");
                del3s();
                return;
            default :
            	System.out.println("잘못된 메뉴 선택입니다. 다시 입력해주세요 (1~3)");
            }
		}
		
	}
	// StudentManager 클래스 내부에 추가
	public void logInMenu() {
		boolean tok = false;
		clearConsole();
		System.out.printf("MOHANO - 로그인 메뉴\n");
		System.out.printf("2025-05-20 Author: wnsgur\n");
	    System.out.print("학번 입력>> ");
	    String studentId = sc.nextLine();
	    del1s();
	    System.out.print("이메일 입력>> ");
	    String email = sc.nextLine();
	    del1s();
	    for (StudentTask s : studentList) {
	        if (s.getStudentId().equals(studentId) && s.getEmail().equals(email)) {
	            System.out.println("로그인 성공! " + s.getName() + "님 환영합니다.");
	            del3s();
	            Handle login = new Handle(s, sc);
	            login.menu();
	            tok = true;
	            break;
	        }
	    }
	    if(!tok) {
	    	System.out.println("로그인 실패: 학번 또는 이메일이 일치하지 않습니다.");
	    	del3s();
		    return;
	    }
	    
	}

    public void signUpMenu() {
		clearConsole();
		System.out.printf("MOHANO - 회원가입 메뉴\n");
		System.out.printf("2025-05-20 Author: wnsgur\n");
        System.out.print("학번 입력>> ");
        String studentId = sc.nextLine();
        del1s();
        // 중복 체크
        if (isStudentIdTaken(studentId)) {
            System.out.println("이미 등록된 학번입니다.");
            del3s();
            return;
        }

        System.out.print("이름 입력>> ");
        String name = sc.nextLine();
        del1s();

        System.out.print("이메일 입력>> ");
        String email = sc.nextLine();
        del1s();

        // 새로운 학생 생성 및 추가
        StudentTask newStudent = new StudentTask(studentId, name, email);
        studentList.add(newStudent);
        System.out.println("회원가입이 완료되었습니다.");
        del3s();
        return;
    }
}
