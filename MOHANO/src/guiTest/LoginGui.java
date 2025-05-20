package guiTest;

import domain.User;
import login.LoginService;
import respository.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static respository.UserRepository.*;

public class LoginGui extends JFrame{

    private JTextField schoolNumField = new JTextField(15);
    private JTextField emailAddressField = new JTextField(15);
    private JButton loginButton = new JButton("로그인");

    private static LoginService loginService = new LoginService();

    public LoginGui() {
        setTitle("로그인");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("학번 (schoolNum):"));
        panel.add(schoolNumField);

        panel.add(new JLabel("이메일 (emailAddress):"));
        panel.add(emailAddressField);

        panel.add(new JLabel(""));
        panel.add(loginButton);

        add(panel);

        setVisible(true); // 이걸 안 하면 GUI 창이 안 뜹니다
        // 로그인 버튼 눌렀을 때 입력값 받아서 처리하는 부분

        loginButton.addActionListener(e -> {
            String schoolNum = schoolNumField.getText().trim();
            String emailAddress = emailAddressField.getText().trim();


            /*
                해당 부분이 로그인 검증 부분입니다.
             */
            UserRepository userRepository = getInstance();
            User user = loginService.login(schoolNum,emailAddress);
            System.out.println(user);
            // 예) DB 확인 메서드 호출 (예: checkLogin(schoolNum, email));
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginGui().setVisible(true);
        });
    }
}