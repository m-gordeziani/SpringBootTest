package com.example.projfixed;

import com.example.projfixed.db.UserRepository;
import com.example.projfixed.model.User;
import com.example.projfixed.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class ProjFixedApplicationTests {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {
        User u = new User("123", "123");
        u.setForgotPasswordToken("test-token");
        this.userRepository.save(u);

        User up = this.userRepository.findByForgotPasswordToken("test-token");
        Assert.isTrue(up.getEmail().equals("123"), "failed");
        String a;
    }

}
