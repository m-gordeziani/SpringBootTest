package com.example.projfixed;

import com.example.projfixed.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjFixedApplicationTests {

    @Autowired
    EmailService emailService;

    @Test
    void contextLoads() {
        emailService.sendMail("mgord17@freeuni.edu.ge", "test", "test");
    }

}
