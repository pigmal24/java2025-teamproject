/**
 * 
 */
package withoutDB;

import java.util.Scanner;

/**
 * @Author : 2023007915 김준혁
 * @Project : 2025 자바 팀프로젝트
 * @프로그램_설명 : main이 있는 class
 * @File_Name : projectMain.java
 * @Todo_List : SMTP를 사용해서 과제 전송 구현, sql 연결, 자동삭제 매소드 테스트
 */
public class ProjectMain {
    public static void clearConsole() {
        // ANSI escape code로 화면을 지움
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void main(String[] args) throws InterruptedException {

    	clearConsole();
    	Scanner sc = new Scanner(System.in);
    	SettingMenu mohano = new SettingMenu(sc);
    	mohano.setMenu();
    	sc.close();
    }
}