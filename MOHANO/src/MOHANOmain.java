import java.util.Scanner;

import console.*;

public class MOHANOmain {
    
    public static void main(String[] args) throws InterruptedException {
    	Func f = new Func();
    	f.clearConsole();
    	Scanner sc = new Scanner(System.in);
    	SettingMenu mohano = new SettingMenu(sc);
    	mohano.mainMenu();
    	sc.close();
    }

}
