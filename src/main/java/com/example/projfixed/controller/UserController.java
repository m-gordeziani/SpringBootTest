package com.example.projfixed.controller;

import com.example.projfixed.model.User;
import com.example.projfixed.service.EmailService;
import com.example.projfixed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @GetMapping("login")
    public String login(){return "login";}

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @PostMapping("register")
    public String registerHandler(@RequestParam String email,
                                  @RequestParam String password,
                                  Model model) {
        User newUser = new User(email, passwordEncoder.encode(password));
        this.emailService.sendMail(newUser.getEmail(), "Confirm Email with token", newUser.getToken());
        this.userService.save(newUser);
        model.addAttribute("user", newUser);

        return "register-confirm";
    }

    @PostMapping("register/confirm")
    public String registerHandler(@RequestParam Long id,
                                  @RequestParam String token) {
        Optional<User> uOpt = this.userService.findById(id);
        if(uOpt.isEmpty()) return "redirect:/";
        User u = uOpt.get();
        if(token.equals(u.getToken())) {
            u.setToken(null);
            u.setActive(true);
            this.userService.save(u);
        }
        return "redirect:/";
    }
}
