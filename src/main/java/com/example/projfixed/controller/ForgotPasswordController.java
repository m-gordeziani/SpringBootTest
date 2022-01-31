package com.example.projfixed.controller;

import com.example.projfixed.db.UserRepository;
import com.example.projfixed.model.User;
import com.example.projfixed.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Optional;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("forgot_password")
    public String forgotPassword() {
        return "forgot_password";
    }

    @PostMapping("forgot_password")
    @ResponseBody
    public String forgotPasswordHandler(ServletRequest request, @RequestParam String email) {
        User u = this.userRepository.findByEmail(email);
        if(u == null) {
            return "no user found with current email: " + email;
        }
        String forgotPasswordToken = User.generateToken();
        u.setForgotPasswordToken(forgotPasswordToken);
        this.userRepository.save(u);

        String HOST = request.getServerName();
        int PORT = request.getServerPort();
        String URL = "/recover_password/" + forgotPasswordToken;
        String forgotPasswordUrl = HOST + ":" + PORT + URL;
        emailService.sendMail(email, "Recovery URL", forgotPasswordUrl);
        return "Recovery URL has been sent to the email";
    }

    @GetMapping("/recover_password/{token}")
    public String recoverPassword(@PathVariable String token, Model model) {
        User u = this.userRepository.findByForgotPasswordToken(token);
        if(u == null) {
            return "redirect:/";
        }
        model.addAttribute("user", u);
        return "recover_password";
    }

    @PostMapping("recover_password")
    @ResponseBody
    public String recoverPassword(@RequestParam Long id, @RequestParam String password) {
        Optional<User> uOpt = this.userRepository.findById(id);
        if(uOpt.isEmpty()) return "User not found";
        User u = uOpt.get();
        u.setPassword(this.passwordEncoder.encode(password));
        this.userRepository.save(u);
        return "Password has been recovered";
    }
}
