package com.example.projfixed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String body){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("springemailsender00@gmail.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);
        this.mailSender.send(msg);
    }
}
