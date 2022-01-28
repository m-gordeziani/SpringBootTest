package com.example.projfixed.controller;

import com.example.projfixed.db.UserRepository;
import com.example.projfixed.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public String registerHandler(@RequestParam String email, @RequestParam String password) {
        User newUser = new User(email, passwordEncoder.encode(password), "USER");
        userRepository.save(newUser);

        return "redirect:login";
    }
}
