package console;

import handle2.Task;
import handle2.User;
import respository.TaskRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Scanner;

public class HandleMenu {

    private User user;
    private Scanner sc;
    private Func fun;
    private List<Task> todayTask;

    TaskRepository taskRepository = TaskRepository.getInstance();

    public HandleMenu(User loginUser, Scanner sc) {
        this.user = loginUser;
        this.sc = sc;
        fun = new Func();
        todayTask = new ArrayList<>();
    }
    public void updateAndPrintTodayTask() {
        todayTask.clear();
        LocalDateTime now = LocalDateTime.now();
        for (Task a : taskRepository.findByUserIdTaskAll(user)) {
            Duration duration = Duration.between(now, a.getDeadline());
            if (!duration.isNegative() && duration.toHours() <= 24) {
                todayTask.add(a);
            }
        }
        int i = 1;
        System.out.println("오늘 할 과제 목록 (24시간 이내 마감):");
        for (Task a : todayTask) {
            System.out.printf("[%2d]: ",i);
            System.out.println(a);
            i++;
        }
    }
    // 옵션1. 과제 추가 메서드
    public void addMenu() { //sql 에다가 X, 자바 내부에서 추가하고 로그아웃 시 반영
        String temp1;
        String temp2;
        String temp3;

        int userId = user.getId(); // user id 를 userId 에 저장
        while(true) {
            fun.clearConsole();
            System.out.printf("MOHANO - %s님의 과제 추가\n",user.getStudentName());
            System.out.printf("돌아가려면 -1을 입력하세요.\n");
            System.out.printf("과목 입력>> ");
            temp1 = sc.nextLine();
            fun.del1s();
            if(temp1.equalsIgnoreCase("-1")) {
                System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getStudentName());
                fun.del3s();
                return;
            }
            System.out.printf("과제 이름 입력>> ");
            temp2 = sc.nextLine();
            fun.del1s();
            if(temp2.equalsIgnoreCase("-1")) {
                System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getStudentName());
                fun.del3s();
                return;
            }
            System.out.printf("제출 기한 입력(yyyy-MM-dd HH:mm)>> ");
            temp3 = sc.nextLine();
            fun.del1s();
            if(temp3.equalsIgnoreCase("-1")) {
                System.out.printf("3초 뒤 %s님의 페이지로 돌아갑니다.\n", user.getStudentName());
                fun.del3s();
                return;
            }
            Task task = new Task(userId,temp1, temp2, temp3);
            taskRepository.save(task, user);

            System.out.printf("%s 추가 완료\n",task.toString());
            fun.del3s();

            return;
        }
    }

    // 옵션2. 과제 변경 메서드
    public void changeMenu() {//sql 에다가 X, 자바 내부에서 수정하고 로그아웃 시 반영


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

            System.out.printf("돌아가려면 -1을 입력하세요.\n");
            System.out.printf("삭제할 과제 taskId 입력>> ");
            taskId = Integer.parseInt(sc.nextLine());
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
            fun.clearConsole();
            //user.removeExpiredAssignments();
            System.out.printf("MOHANO - %s님의 페이지\n", user.getStudentName());
            System.out.printf("1. 과제 추가\n");
            System.out.printf("2. 과제 수정\n");
            System.out.printf("3. 과제 삭제\n");
            System.out.printf("4. 과제 전체 확인\n");
            System.out.printf("5. 로그아웃\n");
            updateAndPrintTodayTask();
            //user.printTodayTasks();
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
                case 5: //sql에다 바뀐 객체 덮어쓰기(update) 필요 (ex: logoutUser) return은 void, 파라미터에 User객체 넣기)
                    // 여기서 taskArr은 따로 매소드를 만들거나, for each로 Task a : allTask로 순회)
                    System.out.println("3초 뒤 로그아웃합니다.");
                    fun.del3s();
                    return;
                default:
                    System.out.println("잘못된 메뉴 선택입니다. 다시 입력해주세요 (1~5)");
            }
        }

    }
}
