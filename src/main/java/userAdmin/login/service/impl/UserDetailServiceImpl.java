//package userAdmin.login.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import userAdmin.login.factory.MyUserDetailsFactory;
//import userAdmin.login.service.LoginService;
//import userAdmin.login.vo.MyUser;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@Service
//public class UserDetailServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private LoginService loginService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String password = loginService.getAccount(username);
//        if(password == null) {
//           throw new UsernameNotFoundException("no user");
//        }
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ADMIN"));
//
//        return MyUserDetailsFactory.create(new User(username, new BCryptPasswordEncoder().encode(password), authorities));
//
//        //從DB TODO
////        MyUser myUser = loginService.getXXXXXX
////        return MyUserDetailsFactory.create(myUser);
//    }
//}
