package console;
//sql에 create, update, read 클래스 만들떄, User class를 맴버로 해서 만들고, 생성자에다 setter, getter 활용해서 집어넣으면 될 것 같습니다.
public class Func {
    public void clearConsole() {
        // ANSI escape code로 화면을 지움
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void del1s() {
        try {
            Thread.sleep(500); // 딜레이 0.5초
        } catch (InterruptedException e) {
            e.printStackTrace(); // 또는 무시해도 OK
        }
    }
    public void del3s() {
        try {
            Thread.sleep(1500); // 딜레이 1.5초
        } catch (InterruptedException e) {
            e.printStackTrace(); // 또는 무시해도 OK
        }
    }
}