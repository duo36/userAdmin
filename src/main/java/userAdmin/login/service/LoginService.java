package userAdmin.login.service;

import org.springframework.security.core.userdetails.User;

public interface LoginService {
    String getAccount(String userName);

    String getTokenRestTemplate(String username, String password);

}
