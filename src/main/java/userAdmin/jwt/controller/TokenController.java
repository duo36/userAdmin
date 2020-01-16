package userAdmin.jwt.controller;

import org.springframework.web.bind.annotation.*;
import userAdmin.jwt.MyJwtGenerate;
import userAdmin.login.vo.MyUser;

@RestController
@RequestMapping("/token")
public class TokenController {

    private  MyJwtGenerate myJwtGenerate;

    public TokenController(MyJwtGenerate myJwtGenerate) {
        this.myJwtGenerate = myJwtGenerate;
    }

    @PostMapping
    public String generate(@RequestBody final MyUser myUser) {

        return myJwtGenerate.generate(myUser);
    }
}
