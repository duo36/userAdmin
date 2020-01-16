package userAdmin.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import userAdmin.register.service.RegisterService;

import java.util.ArrayList;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/index")
    public String getUsers() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        User user = new User(username, password, new ArrayList<>());
        String result =  registerService.register(user);
        if(!"error".equals(result)) {
            model.addAttribute("authorisation", "Bearer " + result);
            return "loginToHome";
        }
        return "redirect:http://localhost:8081/register/index";
    }

}
