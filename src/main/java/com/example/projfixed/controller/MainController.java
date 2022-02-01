package com.example.projfixed.controller;

import com.example.projfixed.db.ProductRepository;
import com.example.projfixed.db.UserRepository;
import com.example.projfixed.model.Product;
import com.example.projfixed.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model, Authentication authentication){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        String nm = authentication.getName();
//        User currentUser = (User) authentication.getPrincipal();
        return "index";
    }

}
