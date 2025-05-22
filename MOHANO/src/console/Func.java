package console;

public class Func {
	public void clearConsole() {
        // ANSI escape code로 화면을 지움
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public void del1s() {
        try {
            Thread.sleep(1000); // 딜레이 1초
        } catch (InterruptedException e) {
            e.printStackTrace(); // 또는 무시해도 OK
        }
    }
    public void del3s() {
        try {
            Thread.sleep(3000); // 딜레이 3초
        } catch (InterruptedException e) {
            e.printStackTrace(); // 또는 무시해도 OK
        }
    }
}
