package userAdmin.login.factory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import userAdmin.login.vo.MyUserDetails;
import userAdmin.login.vo.MyUser;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetailsFactory {

    private MyUserDetailsFactory() {

    }

    //從DB USER來
//    public static MyUserDetails create(MyUser myUser) {
//        return new MyUserDetails(myUser.getId(),
//                myUser.getUsername(),
//                myUser.getPassword(),
//                myUser.getEmail(),
//                mapToGrantedAuthorities(myUser.getRoles()),
//                myUser.getLastPasswordResetDate()
//        );
//    }
    //簡易版
//    public static MyUserDetails create(User user) {
//        return new MyUserDetails(
//                user.getUsername(),
//                123456L,
//                user.getPassword(),
//                "123@gmail.com"
//        );
//    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
