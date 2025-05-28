package console;

import handle2.EmailSender;
import handle2.Task;
import handle2.User;
import respository.TaskRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class HandleMenu {

    private User user;
    private Scanner sc;
    private Func fun;
    private List<Task> todayTask = new ArrayList();

    TaskRepository taskRepository = TaskRepository.getInstance();

    public HandleMenu(User loginUser, Scanner sc) {
        this.user = loginUser;
        this.sc = sc;
        fun = new Func();
        
        /*
         * 기한이 지난 과제들을 removedTask 에 저장
         */
        List<Task> removedTask = taskRepository.removePastTasksAll(user);
        updateTodayTask();
        EmailSender es = new EmailSender();
        es.sendUserTaskEmail(user, todayTask, removedTask);
    }
    
    // 마감시간이 24시간 이내의 Task 들을 todayTask 에 저장
    public void updateTodayTask() {
        todayTask.clear();
        LocalDateTime now = LocalDateTime.now();
        for (Task a : taskRepository.findByUserIdTaskAll(user)) {
            Duration duration = Duration.between(now, a.getDeadline());
            if (!duration.isNegative() && duration.toHours() <= 24) {
                todayTask.add(a);
            }
        }
    }
    
    // 마감시간이 24 시간 이내의 과제들을 출력
    public void PrintTodayTask(){
        int i = 1;
        System.out.println("오늘 할 과제 목록 (24시간 이내 마감):");
        for (Task a : todayTask) {
            System.out.printf("[%2d]: ",i);
            System.out.println(a);
            i++;
        }
    }
        
    // 옵션1. 과제 추가 메서드
    public void addMenu() { 
        String subject; // 과목(subject)
        String title; // 제목(title)
        String deadLine; // 마감기한(deadLine)

        int userId = user.getId(); // user id 를 userId 에 저장
        while(true) {
            fun.clearConsole();
            System.out.printf("MOHANO - %s님의 과제 추가\n",user.getStudentName());
            System.out.printf("돌아가려면 -1을 입력하세요.\n");
            System.out.printf("과목 입력>> ");
            subject = sc.nextLine();
            fun.del1s();
            if(subject.equalsIgnoreCase("-1")) {
                System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getStudentName());
                fun.del3s();
                return;
            }
            System.out.printf("과제 이름 입력>> ");
            title = sc.nextLine();
            fun.del1s();
            if(title.equalsIgnoreCase("-1")) {
                System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getStudentName());
                fun.del3s();
                return;
            }
            
            deadLine = isCheckDeadLine();
            // -1 을 입력받을 경우
            if(deadLine.equalsIgnoreCase("-1")) {
                System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getStudentName());
                fun.del3s();
                return;
            }
            // 정상적으로 입력받았을 경우 Task 를 저장
            else {
            	Task task = new Task(userId,subject, title, deadLine);
            	taskRepository.save(task, user);
                System.out.printf("%s 추가 완료\n",task.toString());
                fun.del3s();
                return;
            }
            
        }
    }
    // 날짜가 유효한 지 검사하는 메서드
    public String isCheckDeadLine() {

        while (true) {
            System.out.printf("제출 기한 입력(yyyy-MM-dd HH:mm)>> ");
            String deadLine = sc.nextLine();
            fun.del1s();

            // -1 입력 시 바로 반환
            if (deadLine.equalsIgnoreCase("-1")) {
                return "-1";
            }

            try {
                LocalDateTime result = LocalDateTime.parse(deadLine, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                
                // 입력받은 날짜가 현재 시간 이전인 경우
                if (result.isBefore(LocalDateTime.now())) {
                    System.out.println("마감 기한은 현재 시간 이후여야 합니다. 다시 입력해주세요.");
                } else {
                    return deadLine; // 유효한 값이므로 반환
                }
                // 입력 형식에 맞지 않을 경우 예외 발생
            } catch (DateTimeParseException e) {
                System.out.println("날짜 형식 오류입니다. 형식: yyyy-MM-dd HH:mm 예: 2025-06-01 13:30");
            }

            System.out.println("돌아가려면 -1을 입력하세요.");
        }
    }
    
    
    // 옵션2. 과제 변경 메서드
    public void changeMenu() {


            int taskId;
            int chM;

            fun.clearConsole();

            System.out.printf("MOHANO - %s님의 과제 수정(index)\n", user.getStudentName());

            // User 의 모든 Task 출력
            List<Task> tasks = taskRepository.findByUserIdTaskAll(user);
            for (Task task : tasks) {
                System.out.println(task);
            }
            System.out.printf("돌아가려면 -1을 입력하세요.\n");


            System.out.printf("수정할 과제 index 입력>> ");
            taskId = Integer.parseInt(sc.nextLine());
            fun.del1s();
            if (taskId == -1) {
                System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getStudentName());
                fun.del3s();
                return;
            }

            // taskId 가 범위에 포함되지 않을 경우 다시 입력
            Task validation = taskRepository.findByTaskId(taskId);
            if(validation == null) {
            	System.out.println("id 에 해당하는 과제가 존재하지 않습니다.");
            	return;
            }
            
            while (true) {
                fun.clearConsole();
                System.out.printf("MOHANO - %s님의 과제 수정(member)\n", user.getStudentName());
                System.out.printf("수정 요소 입력\n");
                System.out.printf("1. 과목, 2. 과제명, 3. 제출 기한\n");
                System.out.printf("돌아가려면 -1을 입력하세요.\n");
                System.out.printf(">> ");

                chM = Integer.parseInt(sc.nextLine());
                fun.del1s();
                switch (chM) {
                    case 1:
                        System.out.printf("과목명 입력>> ");
                        String changeSubject = sc.nextLine();
                        Task updateSubjectTask = taskRepository.updateSubject(taskId, changeSubject);
                        System.out.printf("%s 수정 완료\n", updateSubjectTask.toString());
                        fun.del1s();
                        break;
                    // 옵션2. 과제명(Title) 변경
                    case 2:
                        System.out.printf("과제명 입력>> ");
                        String changeTitle = sc.nextLine();
                        Task updateTitleTask = taskRepository.updateTitle(taskId, changeTitle);
                        System.out.printf("%s 수정 완료\n", updateTitleTask.toString());
                        fun.del1s();
                        break;
                    // 옵션3. 제출 기한(deadLine) 변경
                    case 3:
                        System.out.printf("제출 기한 입력(yyyy-MM-dd HH:mm)>> ");
                        String changeDeadLine = sc.nextLine();

                        /******************************
                             // 매우중요::::: deadLine 입력 양식에 맞지 않을 경우 다시 입력
                         *****************************/

                        Task updateDeadlineTask = taskRepository.updateDeadLine(taskId, changeDeadLine);
                        fun.del1s();
                        System.out.printf("%s 수정 완료\n", updateDeadlineTask.toString());
                        fun.del3s();
                        break;
                    case -1:
                        System.out.printf("index 선택 화면으로 돌아갑니다.\n");
                        fun.del3s();
                        return;
                }
            }
    }

    // 옵션3. 과제 삭제 메서드
    public void removeMenu() {//sql 에다가 X, 자바 내부에서 삭제하고 로그아웃 시 반영

        int taskId;
        while(true) {
            fun.clearConsole();
            System.out.printf("MOHANO - %s님의 과제 삭제\n",user.getStudentName());

            List<Task>  tasks = taskRepository.findByUserIdTaskAll(user);
            for (Task task : tasks) {
                System.out.println(task);
            }

            // 삭제할 과제 taskId 를 입력
            // taskId 에 해당하는 데이터가 없을 경우 return;
            System.out.printf("돌아가려면 -1을 입력하세요.\n");
            System.out.printf("삭제할 과제 taskId 입력>> ");
            taskId = Integer.parseInt(sc.nextLine());
            
            // taskId 가 범위에 포함되지 않을 경우 다시 입력
            Task validation = taskRepository.findByTaskId(taskId);
            if(validation == null) {
            	System.out.println("id 에 해당하는 과제가 존재하지 않습니다. 메뉴로 돌아갑니다.");
            	fun.del3s();
            	return;
            }
            fun.del1s();
            if(taskId == -1) {
                System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getStudentName());
                fun.del3s();
                return;
                // Task 삭제 진행
            }else {
                Task removedTask = taskRepository.removeById(taskId);
                System.out.printf("%s 삭제 성공",removedTask.toString());
                //fun.del3s();
                return;
            }
        }

    }


    // 옵션 4.
    // 과제 목록 출력 메서드
    public void showAllMenu() {

        while(true) {
            fun.clearConsole();
            System.out.printf("MOHANO - %s님의 전체 과제\n",user.getStudentName());
            List<Task>  tasks = taskRepository.findByUserIdTaskAll(user);

            for (Task task : tasks) {
                System.out.println(task);
            }
            System.out.printf("돌아가려면 아무키나 입력하세요.\n");
            sc.nextLine();
            break;
        }

    }
    public void menu() {

        int ch;
        while (true) {
        	updateTodayTask();
        	// menu() 메서드가 호출될 때 마감기한이 지난 메서드는 삭제
        	//taskRepository.removePastTasksAll(user);
            fun.clearConsole();
            System.out.printf("MOHANO - %s님의 페이지\n", user.getStudentName());
            System.out.printf("1. 과제 추가\n");
            System.out.printf("2. 과제 수정\n");
            System.out.printf("3. 과제 삭제\n");
            System.out.printf("4. 과제 전체 확인\n");
            System.out.printf("5. 로그아웃\n");
            PrintTodayTask();
            System.out.printf(">> ");

            ch = Integer.parseInt(sc.nextLine());
            fun.del1s();
            switch (ch) {
                // 옵션1. 과제 추가
                case 1:
                    addMenu();
                    break;
                case 2:
                    changeMenu();
                    break;
                // 옵션3. 과제 삭제
                case 3:
                    removeMenu();
                    break;
                // 옵션4. 모든 과제 출력
                case 4 : 
                	showAllMenu();
                	break;
                case 5: 
                    System.out.println("3초 뒤 로그아웃합니다.");
                    fun.del3s();
                    return;
                default:
                    System.out.println("잘못된 메뉴 선택입니다. 다시 입력해주세요 (1~5)");
                    fun.del3s();
            }
        }

    }
}