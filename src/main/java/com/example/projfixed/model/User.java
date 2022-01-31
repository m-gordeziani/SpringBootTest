package com.example.projfixed.model;

import net.bytebuddy.utility.RandomString;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.UUID;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;

    private Boolean active;

    private String token;

    private String forgotPasswordToken;

    public static String generateToken() {
        return UUID.randomUUID().toString() +
                UUID.randomUUID().toString();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = "USER";
        this.active = false;
        this.token = generateToken();
    }

    public User(String email, String password, String role, Boolean active){
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public User(){}

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getForgotPasswordToken() {
        return forgotPasswordToken;
    }

    public void setForgotPasswordToken(String forgotPasswordToken) {
        this.forgotPasswordToken = forgotPasswordToken;
    }
}
