package com.example.projfixed.service;

import com.example.projfixed.model.User;

import java.util.Optional;

public interface UserServiceInterface {

    User findByEmail(String email);

    Optional<User> findById(Long id);

    User save(User u);

    User findByRole(String role);

    User findByForgotPasswordToken(String token);
}
