package userAdmin.login.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MyUser {
    private Long id;

    private String username;

    private String email;

    private String password;

    private Date lastPasswordResetDate;

    private String role;
}
