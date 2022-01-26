package com.example.projfixed.db;

import com.example.projfixed.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        User user = new User("user",passwordEncoder.encode("user123"),"USER");
        User admin = new User("admin",passwordEncoder.encode("admin123"),"ADMIN");

        List<User> users = Arrays.asList(user,admin);

        this.userRepository.saveAll(users);
    }
}
