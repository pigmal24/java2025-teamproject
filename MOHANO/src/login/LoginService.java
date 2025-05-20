package login;


import domain.User;
import respository.UserRepository;

import java.util.Map;

import static respository.UserRepository.*;

public class LoginService {


    // 싱글톤 userRepository 반환
    private UserRepository userRepository = getInstance();

    public LoginService() {

    }

    public User login(String studentNum, String emailAddress) {

        // 아이디(studentNum) 의 해당하는 객체를 찾은 후
        // 해당 객체의 비밀번호와 매개변수의 비밀번호가 같으면 해당 객체를 반환
        return userRepository.findByLoginId(studentNum)
                .filter(m -> m.getEmailAddress().equals(emailAddress)).orElse(null);

    }
}
