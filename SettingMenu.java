package console;

import handle2.User;
import respository.UserRepository;

import java.util.Scanner;

public class SettingMenu {
    private Scanner sc;
    Func fun;

    UserRepository userRepository = UserRepository.getInstance();
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
            System.out.printf(">> ");
            choice = Integer.parseInt(sc.nextLine());
            fun.del1s();
            switch (choice) {
                // 로그인
                case 1 :
                    logInMenu();
                    break;
                // 회원가입
                case 2 :
                    signUpMenu();
                    break;
                // 종료
                case 3 :
                    System.out.println("MOHANO를 종료합니다.");
                    fun.del3s();
                    return;
                default :
                    System.out.println("잘못된 메뉴 선택입니다. 다시 입력해주세요 (1~3)");
                    fun.del3s();
            }
        }
    }
    public void logInMenu() {

        while(true) {

            fun.clearConsole();
            System.out.printf("MOHANO - 로그인 메뉴\n");
            System.out.print("학번 입력>> ");
            String studentId = sc.nextLine();
            fun.del1s();

            System.out.print("이메일 입력>> ");
            String email = sc.nextLine();
            fun.del1s();
            User user = userRepository.findByLoginId(studentId).filter(m -> m.getEmailAddress().equals(email)).orElse(null);
            if(user != null) {
                System.out.println("로그인 성공");
                HandleMenu handle = new HandleMenu(user,sc);
                handle.menu();
                break;
            }
            // 로그인 실패 시 다시 로그인
            else  {
                System.out.println("로그인 실패");
                System.out.println("다시 입력해주세요");
                fun.del3s();
            }
        }
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

        System.out.println("앱 비밀번호 입력>> ");
        String smtpPass = sc.nextLine();
        
        // 객체 생성 후 입력한 값들 설정
        User user = new User();

        user.setSchoolNum(studentId);
        user.setStudentName(name);
        user.setEmailAddress(email);
        user.setSmtpPass(smtpPass);
        userRepository.save(user);
        System.out.printf("회원가입 완료!\n");
        fun.del3s();
        return;
    }

}