package com.example.projfixed.db;

import com.example.projfixed.model.Product;
import com.example.projfixed.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ProductRepository productRepository;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {

        User user = new User("user",passwordEncoder.encode("user123"),"USER", true);
        User admin = new User("admin",passwordEncoder.encode("admin123"),"ADMIN", true);

        User mg = new User("mgord17@freeuni.edu.ge",passwordEncoder.encode("123"),"USER", true);
        mg.setForgotPasswordToken("mg");
        mg.setForgotPasswordDate(new Date());

        List<User> users = Arrays.asList(user,admin, mg);
        this.userRepository.saveAll(users);

        Product p1 = new Product("hat", 20, 59.99, "hat-black.jpg");
        Product p2 = new Product("hoodie", 30, 89.99, "hoodie.jpg");
        Product p3 = new Product("panna", 60, 39.99, "panna.jpg");
        Product p4 = new Product("shirt", 10, 30.00, "shirt.jpg");

        List<Product> products = Arrays.asList(p1, p2, p3, p4);
        this.productRepository.saveAll(products);
    }
}
