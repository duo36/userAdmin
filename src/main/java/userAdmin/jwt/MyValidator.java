package userAdmin.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import userAdmin.login.service.LoginService;
import userAdmin.login.vo.MyUser;

@Component
public class MyValidator {
    private String secret = "myTokenmyTokenmyTokenmyTokenmyTokenmyTokenmyTokenmyTokenmyTokenmyToken";

    public MyUser validate(String token) {

        MyUser myUser = null;

        try{

            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            myUser = new MyUser();

            myUser.setUsername(body.getSubject());
            myUser.setId(Long.parseLong((String)body.get("userId")));
            myUser.setRole((String)body.get("role"));
            myUser.setEmail((String)body.get("email"));

        } catch (Exception e) {
        }
        return myUser;
    }
}
