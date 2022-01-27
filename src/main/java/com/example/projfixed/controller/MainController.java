package com.example.projfixed.controller;

import com.example.projfixed.db.ProductRepository;
import com.example.projfixed.db.UserRepository;
import com.example.projfixed.model.Product;
import com.example.projfixed.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductRepository productRepository;

    private final String pathToProductImages = System.getProperty("user.dir")+"/src/main/resources/static/products/";

    @GetMapping("/")
    public String index(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("login")
    public String login(){return "login";}

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @PostMapping("register")
    public String registerHandler(@RequestParam String email, @RequestParam String password) {
        User newUser = new User(email, passwordEncoder.encode(password), "USER");
        userRepository.save(newUser);

        return "redirect:login";
    }

    @PostMapping("products/add")
    public String addNewProduct(@RequestParam String name,
                                @RequestParam Integer quantity,
                                @RequestParam Double price,
                                @RequestParam MultipartFile img
                                ) {

        try {

            byte[] bytes = img.getBytes();
            Path path = Paths.get(this.pathToProductImages + img.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product newProduct = new Product(name, quantity, price, img.getOriginalFilename());
        this.productRepository.save(newProduct);
        return "redirect:/";
    }

    @GetMapping("products/update")
    public User updateProduct(){

        return this.userRepository.findByEmail("admin");
    }
}
