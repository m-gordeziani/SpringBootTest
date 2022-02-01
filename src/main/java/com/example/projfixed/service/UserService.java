package com.example.projfixed.service;

import com.example.projfixed.db.UserRepository;
import com.example.projfixed.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInterface{

    @Autowired
    private UserRepository repository;

    @Override
    public User findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public User save(User u) {
        return this.repository.save(u);
    }

    @Override
    public User findByRole(String role) {
        return this.repository.findByRole(role);
    }

    @Override
    public User findByForgotPasswordToken(String token) {
        return this.repository.findByForgotPasswordToken(token);
    }

}
