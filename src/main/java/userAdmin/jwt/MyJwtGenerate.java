package userAdmin.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import userAdmin.login.vo.MyUser;

@Component
public class MyJwtGenerate {

    public String generate(MyUser myUser) {
        Claims claims = Jwts.claims()
                .setSubject(myUser.getUsername());
        claims.put("userId", String.valueOf(myUser.getId()));
        claims.put("role", myUser.getRole());
        claims.put("email", myUser.getEmail());

        return Jwts.builder()
                .setClaims(claims).signWith(SignatureAlgorithm.HS256, "myTokenmyTokenmyTokenmyTokenmyTokenmyTokenmyTokenmyTokenmyTokenmyToken")
                .compact();

    }
}
