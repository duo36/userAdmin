package userAdmin.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import userAdmin.login.service.LoginService;
import userAdmin.login.vo.MyUser;
import userAdmin.login.vo.MyUserDetails;

import java.util.List;

@Component
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private MyValidator myValidator;

    @Autowired
    private LoginService loginService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        MyAuthenticationToken myAuthenticationToken = (MyAuthenticationToken) usernamePasswordAuthenticationToken;

        String token = myAuthenticationToken.getToken();

        MyUser myUser = myValidator.validate(token);

        if (myUser == null) {
            throw new RuntimeException("JWT is incorrect");
        }

        String password = loginService.getAccount(myUser.getUsername());
        if(password == null) {
            throw new UsernameNotFoundException("no user");
        }

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(myUser.getRole());

        return new MyUserDetails(myUser.getUsername(), myUser.getId(), token, grantedAuthorities, myUser.getEmail());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (MyAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
