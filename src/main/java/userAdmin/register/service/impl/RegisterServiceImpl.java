package userAdmin.register.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import userAdmin.login.vo.MyUser;
import userAdmin.register.service.RegisterService;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Service
public class RegisterServiceImpl implements RegisterService {

    //註冊帳密先用全域變數,Map<name,password>
    Map<String, String> accountMap = new HashMap<>();

    private final String TOKEN_URL = "http://localhost:8081/token";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String register(User user) {

        try{
            int num = accountMap.size();
            accountMap.put(user.getUsername(), user.getPassword());
            if (num == accountMap.size()) {
                return "error";
            }
        }catch (Exception e) {
            return "error";
        }
        MyUser myUser = new MyUser();
        myUser.setUsername(user.getUsername());
        myUser.setId(12345L);
        myUser.setRole("admin");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MyUser> request = new HttpEntity<>(myUser, headers);
        return restTemplate.postForObject(TOKEN_URL, request, String.class);
    }

    @Override
    public String getPassword(String username) {
        return accountMap.get(username);
    }
}
