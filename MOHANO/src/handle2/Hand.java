package handle2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;


public class Hand {

    private ArrayList<Task> todayTask;//user 객체의 allTask에서 오늘자 과제를 뽑아 ArrayList로 만들기
    public Hand() {
        todayTask = new ArrayList<>();
    }
    // 과제 추가 및 todayTask 자동 갱신

    /*
    public void addAll(Task assignment) {
        //taskTable.allTask.add(assignment);
        updateTodayTask();
    }
    //과제 삭제 및 todayTask 자동 갱신
    public Task removeAssignmentToAll(int index) {
        //Task res = taskTable.allTask.get(index);
        //taskTable.allTask.remove(index);
        updateTodayTask();
        return res;
    }
    //특정 index 과제 return
    public Task get(int index) {
        return taskTable.allTask.get(index);
    }
    //특정 index 과제 수정
    public void set(int index, Task chTask) {
        taskTable.allTask.set(index, chTask);
    }

    // todayTask 갱신 (24시간 이내 마감 과제)
    public void updateTodayTask() {
        todayTask.clear();
        LocalDateTime now = LocalDateTime.now();
        for (Task a : taskTable.allTask) {
            Duration duration = Duration.between(now, a.getDeadline());
            if (!duration.isNegative() && duration.toHours() <= 24) {
                todayTask.add(a);
            }
        }
    }
    // 지난 과제 자동 삭제
    public void removeExpiredAssignments() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Task> iterator = taskTable.allTask.iterator();

        while (iterator.hasNext()) {
            Task a = iterator.next();
            if (a.getDeadline().isBefore(now)) {
                iterator.remove();
            }
        }
        // todayTask도 갱신해줘야 함
        updateTodayTask();
    }

    // 전체 과제 출력
    public void printAllTasks() {
        int i = 0;
        System.out.println("전체 과제 목록:");
        for (Task a : taskTable.allTask) {
            System.out.printf("[%2d]: ",i);
            System.out.println(a);
            i++;
        }
    }

    // 오늘 할 과제 출력
    public void printTodayTasks() {
        int i = 0;
        System.out.println("오늘 할 과제 목록 (24시간 이내 마감):");
        for (Task a : todayTask) {
            System.out.printf("[%2d]: ",i);
            System.out.println(a);
            i++;
        }
    }
    @Override
    public String toString() {
        return "학번: "+schoolNum+"이름: "+studentName+"메일 주소: "+emailAddress+"전체 과제 수: "+taskTable.allTask.size()+"오늘 과제 수:"+todayTask.size();
    }


     */
}
