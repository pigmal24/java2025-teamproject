package console;

import java.util.Scanner;
import handle.*;

public class SettingMenu {
	private Scanner sc;
	Func fun;
	public SettingMenu(Scanner sc) {
		this.sc = sc;
		fun = new Func();
	}
	
	public void mainMenu() {
		int choice = 0;
		while(true) {
			fun.clearConsole();
			System.out.printf("MOHANO - 과제 관리 시스템\n");
			System.out.printf("1. 로그인\n");
			System.out.printf("2. 회원가입\n");
			System.out.printf("3. MOHANO 종료\n");
			System.out.printf("2025-05-20 Author: wnsgur\n");
			System.out.printf(">> ");
			choice = Integer.parseInt(sc.nextLine()); 
			fun.del1s();
			switch (choice) {
            case 1 : 
            	logInMenu();
            	break;
            case 2 : 
            	signUpMenu();
            	break;
            case 3 : 
                System.out.println("MOHANO를 종료합니다.");
                fun.del3s();
                return;
            default :
            	System.out.println("잘못된 메뉴 선택입니다. 다시 입력해주세요 (1~3)");
            }
		}
	}
	public void logInMenu() {
		fun.clearConsole();
		System.out.printf("MOHANO - 로그인 메뉴\n");
		System.out.printf("2025-05-20 Author: wnsgur\n");
	    System.out.print("학번 입력>> ");
	    String studentId = sc.nextLine();
	    fun.del1s();
	    System.out.print("이메일 입력>> ");
	    String email = sc.nextLine();
	    fun.del1s();
	    /* sql로 접근해서 read 한 다음 User 객체를 return해서 HandleMenu에다 집어넣는거 필요, loginUser 객체에서 User 맴버를 만들고
	     * loginUser 생성자에서 sql 연결 및 read해서 get해온다는 마인드
	     * HandleMenu handle = new HandleMenu(loginUser(studentId, email));
	     * handle.mainMenu();
	     * 
	    try { 
	    	
	    }catch() {
	    	
	    }
	    */
	}
    public void signUpMenu() { //sql user table에다가 create하는 매소드 필요(ex: signUpUser)
    	fun.clearConsole();
		System.out.printf("MOHANO - 회원가입 메뉴\n");
		System.out.printf("2025-05-20 Author: wnsgur\n");
        System.out.print("학번 입력>> ");
        String studentId = sc.nextLine();
        fun.del1s();

        System.out.print("이름 입력>> ");
        String name = sc.nextLine();
        fun.del1s();

        System.out.print("이메일 입력>> ");
        String email = sc.nextLine();
        fun.del1s();
        /* sql로 접근해서 중복 확인 후 새 user table을 create 
	     * SignUpUSer signUp = new SignUpUser(String studentId, String name, String email)
	     * 
	     * 
	    try { 
	    	
	    }catch() {
	    	
	    }
	    */
        fun.del3s();
        return;
    }
	
}
