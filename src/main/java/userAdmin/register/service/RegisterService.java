package userAdmin.register.service;

import org.springframework.security.core.userdetails.User;

public interface RegisterService {
    String register(User user);

    String getPassword(String username);

}
