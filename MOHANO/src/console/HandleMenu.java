package console;

import java.util.Scanner;

import handle.*;

public class HandleMenu {
	private Hand user;
	private Scanner sc;
	private Func fun;
	public HandleMenu(User loginUser, Scanner sc){
		this.user =(Hand)loginUser;
		this.sc = sc;
		fun = new Func();
	}
    public void addMenu() { //sql 에다가 X, 자바 내부에서 추가하고 로그아웃 시 반영
    	String temp1;
    	String temp2;
    	String temp3;
    	while(true) {
    		fun.clearConsole();
    		System.out.printf("MOHANO - %s님의 과제 추가\n",user.getName());
    		System.out.printf("2025-05-20 Author: wnsgur\n");
    		System.out.printf("돌아가려면 -1을 입력하세요.\n");
    		System.out.printf("과목 입력>> ");
    		temp1 = sc.nextLine();
    		fun.del1s();
    		if(temp1.equalsIgnoreCase("-1")) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getName());
    			fun.del3s();
    			return;
    		}
    		System.out.printf("과제 이름 입력>> ");
    		temp2 = sc.nextLine();
    		fun.del1s();
    		if(temp2.equalsIgnoreCase("-1")) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getName());
    			fun.del3s();
    			return;
    		}
    		System.out.printf("제출 기한 입력(yyyy-MM-dd HH:mm)>> ");
    		temp3 = sc.nextLine();
    		fun.del1s();
    		if(temp3.equalsIgnoreCase("-1")) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getName());
    			fun.del3s();
    			return;
    		}
    		Task add = new Task(temp1, temp2, temp3);
    		user.addAll(add);
    		System.out.printf("%s 추가 완료\n",add.toString());
    		fun.del3s();
    	}
    }
    public void changeMenu() {//sql 에다가 X, 자바 내부에서 수정하고 로그아웃 시 반영
    	int ch;
    	int chM;
    	while(true) {
    		fun.clearConsole();
        	System.out.printf("MOHANO - %s님의 과제 수정(index)\n",user.getName());
    		System.out.printf("2025-05-20 Author: wnsgur\n");
    		user.printAllTasks();
    		System.out.printf("돌아가려면 -1을 입력하세요.\n");
    		System.out.printf("수정할 과제 index 입력>> ");
			ch = Integer.parseInt(sc.nextLine()); 
			fun.del1s();
    		if(ch == -1) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getName());
    			fun.del3s();
    			return;
    		}
    		while(true) {
        		fun.clearConsole();
            	System.out.printf("MOHANO - %s님의 과제 수정(member)\n",user.getName());
        		System.out.printf("수정 요소 입력\n");
        		System.out.printf("1. 과목, 2. 과제명, 3. 제출 기한\n");
        		System.out.printf("돌아가려면 -1을 입력하세요.\n");
        		System.out.printf(">> ");
        		Task change = user.get(ch);
        		chM = Integer.parseInt(sc.nextLine());
        		fun.del1s();
        		switch(chM) {
        		case 1:
        			System.out.printf("과목명 입력>> ");
        			change.setSubject(sc.nextLine());
        			System.out.printf("%s 수정 완료\n",change.toString());
        			fun.del1s();
        			break;
        		case 2:
        			System.out.printf("과제명 입력>> ");
        			change.setTitle(sc.nextLine());
        			System.out.printf("%s 수정 완료\n",change.toString());
        			fun.del1s();
        			break;
        		case 3:
        			System.out.printf("제출 기한 입력(yyyy-MM-dd HH:mm)>> ");
        			change.setDeadline(sc.nextLine());
        			fun.del1s();
        			user.set(ch, change);
        			System.out.printf("%s 수정 완료\n",change.toString());
        			fun.del3s();
        			break;
        		case -1:
        			System.out.printf("index 선택 화면으로 돌아갑니다.\n");
        	        try {
        	            Thread.sleep(3000); // 딜레이 3초
        	        } catch (InterruptedException e) {
        	            e.printStackTrace(); // 또는 무시해도 OK
        	        }
        			return;
        		}
    		}
    		
    	}
    }
    
    public void removeMenu() {//sql 에다가 X, 자바 내부에서 삭제하고 로그아웃 시 반영
    	int ch;
    	while(true) {
    		fun.clearConsole();
        	System.out.printf("MOHANO - %s님의 과제 삭제\n",user.getName());
    		System.out.printf("2025-05-20 Author: wnsgur\n");
    		user.printAllTasks();
    		System.out.printf("돌아가려면 -1을 입력하세요.\n");
    		System.out.printf("삭제할 과제 index 입력>> ");
    		ch = Integer.parseInt(sc.nextLine()); 
    		fun.del1s();
    		if(ch == -1) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getName());
    			fun.del3s();
    			return;
    		}else {
    			Task temp = user.removeAssignmentToAll(ch);
    			System.out.printf("%s 삭제 성공",temp.toString());
    			fun.del3s();
    		}
    	}
    }
    
    public void showAllMenu() {
    	while(true) {
    		fun.clearConsole();
        	System.out.printf("MOHANO - %s님의 전체 과제\n",user.getName());
    		System.out.printf("2025-05-20 Author: wnsgur\n");
    		user.printAllTasks();
    		System.out.printf("돌아가려면 아무키나 입력하세요.\n");
			sc.nextLine();
			break;
    	}
    	
    }
	
	public void menu() {
		int ch;
		while(true) {
			fun.clearConsole();
			user.removeExpiredAssignments();
			System.out.printf("MOHANO - %s님의 페이지\n",user.getName());
			System.out.printf("1. 과제 추가\n");
			System.out.printf("2. 과제 수정\n");
			System.out.printf("3. 과제 삭제\n");
			System.out.printf("4. 과제 전체 확인\n");
			System.out.printf("5. 로그아웃\n");
			System.out.printf("2025-05-20 Author: wnsgur\n");
			user.printTodayTasks();
			System.out.printf(">> ");
			
			ch = Integer.parseInt(sc.nextLine());
			fun.del1s();
			switch (ch) {
            case 1 : addMenu();
            	break;
            case 2 : changeMenu();
            	break;
            case 3 : removeMenu();
            	break;
            case 4 : showAllMenu();
            	break;
            case 5 : //sql에다 바뀐 객체 덮어쓰기(update) 필요 (ex: logoutUser) return은 void, 파라미터에 User객체 넣기)
            		// 여기서 taskArr은 따로 매소드를 만들거나, for each로 Task a : allTask로 순회)
                System.out.println("3초 뒤 로그아웃합니다.");
                fun.del3s();
                return;
            default : System.out.println("잘못된 메뉴 선택입니다. 다시 입력해주세요 (1~5)");
            }
		}
		
	}
    
	
}
