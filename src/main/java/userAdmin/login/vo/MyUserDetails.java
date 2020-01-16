package userAdmin.login.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetails implements UserDetails {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private String token;

    public MyUserDetails(String username, Long id, String token, Collection<? extends GrantedAuthority> authorities, String email) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.authorities = authorities;
        this.email = email;
    }
//
//    public MyUserDetails(String username, Long id, String password, String email) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
