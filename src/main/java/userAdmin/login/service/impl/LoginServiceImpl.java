package userAdmin.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import userAdmin.login.service.LoginService;
import userAdmin.login.vo.MyUser;
import userAdmin.register.service.RegisterService;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RestTemplate restTemplate;

    private final String TOKEN_URL = "http://localhost:8081/token";

    @Override
    public String getAccount(String username) {
        return registerService.getPassword(username);
    }

    @Override
    public String getTokenRestTemplate(String username, String oriPassword) {
        String password = registerService.getPassword(username);

        if(password == null || !password.equals(oriPassword)) {
            throw new UsernameNotFoundException("no user");
        }
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setId(12345L);
        myUser.setRole("admin");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MyUser> request = new HttpEntity<>(myUser, headers);
        return restTemplate.postForObject(TOKEN_URL, request, String.class);
    }
}
