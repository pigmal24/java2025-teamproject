/**
 * 
 */
package handle;

import java.util.Scanner;

/**
 * @Author : 2023007915 김준혁
 * @Project : 2025 자바 팀프로젝트 MOHANO
 * @프로그램_설명 : 로그인한 대상에 맞는 studentTask 핸들링
 * @File_Name : Handle.java
 */
public class Handle {
	private StudentTask myTask;
	private Scanner sc;
	public Handle(StudentTask loginTask, Scanner sc) {
		this.myTask = loginTask;
		this.sc =sc;
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
    
    public void addMenu() {
    	String temp1;
    	String temp2;
    	String temp3;
    	while(true) {
    		clearConsole();
    		System.out.printf("MOHANO - %s님의 과제 추가\n",myTask.getName());
    		System.out.printf("2025-05-20 Author: wnsgur\n");
    		System.out.printf("돌아가려면 -1을 입력하세요.\n");
    		System.out.printf("과목 입력>> ");
    		temp1 = sc.nextLine();
    		del1s();
    		if(temp1.equalsIgnoreCase("-1")) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", myTask.getName());
    			del3s();
    			return;
    		}
    		System.out.printf("과제 이름 입력>> ");
    		temp2 = sc.nextLine();
    		del1s();
    		if(temp2.equalsIgnoreCase("-1")) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", myTask.getName());
    			del3s();
    			return;
    		}
    		System.out.printf("제출 기한 입력(yyyy-MM-dd HH:mm)>> ");
    		temp3 = sc.nextLine();
    		del1s();
    		if(temp3.equalsIgnoreCase("-1")) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", myTask.getName());
    			del3s();
    			return;
    		}
    		Assignment add = new Assignment(temp1, temp2, temp3);
    		myTask.addAssignmentToAll(add);
    		System.out.printf("%s 추가 완료",add.toString());
    		del3s();
    	}
    }
    public void changeMenu() {
    	int ch;
    	int chM;
    	while(true) {
    		clearConsole();
        	System.out.printf("MOHANO - %s님의 과제 수정(index)\n",myTask.getName());
    		System.out.printf("2025-05-20 Author: wnsgur\n");
    		myTask.printAllTasks();
    		System.out.printf("돌아가려면 -1을 입력하세요.\n");
    		System.out.printf("수정할 과제 index 입력>> ");
			ch = Integer.parseInt(sc.nextLine()); 
			del1s();
    		if(ch == -1) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", myTask.getName());
    			del3s();
    			return;
    		}
    		while(true) {
        		clearConsole();
            	System.out.printf("MOHANO - %s님의 과제 수정(member)\n",myTask.getName());
        		System.out.printf("수정 요소 입력\n");
        		System.out.printf("1. 과목, 2. 과제명, 3. 제출 기한\n");
        		System.out.printf("돌아가려면 -1을 입력하세요.\n");
        		System.out.printf(">> ");
        		Assignment change = myTask.get(ch);
        		chM = Integer.parseInt(sc.nextLine());
        		del1s();
        		switch(chM) {
        		case 1:
        			System.out.printf("과목명 입력>> ");
        			change.setSubject(sc.nextLine());
        			del1s();
        		case 2:
        			System.out.printf("과제명 입력>> ");
        			change.setTitle(sc.nextLine());
        			del1s();
        		case 3:
        			System.out.printf("제출 기한 입력(yyyy-MM-dd HH:mm)>> ");
        			change.setDeadline(sc.nextLine());
        			del1s();
        			myTask.set(ch, change);
        			System.out.printf("%s 수정 완료",change.toString());
        			del3s();
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
    
    public void removeMenu() {
    	int ch;
    	while(true) {
    		clearConsole();
        	System.out.printf("MOHANO - %s님의 과제 삭제\n",myTask.getName());
    		System.out.printf("2025-05-20 Author: wnsgur\n");
    		myTask.printAllTasks();
    		System.out.printf("돌아가려면 -1을 입력하세요.\n");
    		System.out.printf("삭제할 과제 index 입력>> ");
    		ch = Integer.parseInt(sc.nextLine()); 
    		del1s();
    		if(ch == -1) {
    			System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", myTask.getName());
    			del3s();
    			return;
    		}else {
    			Assignment temp = myTask.removeAssignmentToAll(ch);
    			System.out.printf("%s 삭제 성공",temp.toString());
    			del3s();
    		}
    	}
    }
    
    public void showAllMenu() {
    	while(true) {
    		clearConsole();
        	System.out.printf("MOHANO - %s님의 전체 과제\n",myTask.getName());
    		System.out.printf("2025-05-20 Author: wnsgur\n");
    		myTask.printAllTasks();
    		System.out.printf("돌아가려면 아무키나 입력하세요.\n");
			sc.nextLine();
			break;
    	}
    	
    }
	
	public void menu() {
		int ch;
		while(true) {
			clearConsole();
			myTask.removeExpiredAssignments();
			System.out.printf("MOHANO - %s님의 페이지\n",myTask.getName());
			System.out.printf("1. 과제 추가\n");
			System.out.printf("2. 과제 수정\n");
			System.out.printf("3. 과제 삭제\n");
			System.out.printf("4. 과제 전체 확인\n");
			System.out.printf("5. 로그아웃\n");
			System.out.printf("2025-05-20 Author: wnsgur\n");
			myTask.printTodayTasks();
			System.out.printf(">> ");
			
			ch = Integer.parseInt(sc.nextLine());
			del1s();
			switch (ch) {
            case 1 : addMenu();
            	break;
            case 2 : changeMenu();
            	break;
            case 3 : removeMenu();
            	break;
            case 4 : showAllMenu();
            	break;
            case 5 :
                System.out.println("3초 뒤 로그아웃합니다.");
                del3s();
                return;
            default : System.out.println("잘못된 메뉴 선택입니다. 다시 입력해주세요 (1~5)");
            }
		}
		
	}
}
