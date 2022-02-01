package com.example.projfixed.controller;

import com.example.projfixed.db.OrderRepository;
import com.example.projfixed.db.ProductRepository;
import com.example.projfixed.db.UserRepository;
import com.example.projfixed.model.Order;
import com.example.projfixed.model.Product;
import com.example.projfixed.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final String pathToProductImages = System.getProperty("user.dir")+"/src/main/resources/static/products/";
    private final String pathToCompiledProductImages = System.getProperty("user.dir")+"/target/classes/static/products/";

    @PostMapping("products/add")
    public String addNewProduct(@RequestParam String name,
                                @RequestParam Integer quantity,
                                @RequestParam Double price,
                                @RequestParam MultipartFile img
    ) {

        try {

            byte[] bytes = img.getBytes();
            Path path = Paths.get(this.pathToProductImages + img.getOriginalFilename());
            Path pathCompiled = Paths.get(this.pathToCompiledProductImages + img.getOriginalFilename());
            Files.write(path, bytes);
            Files.write(pathCompiled, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product newProduct = new Product(name, quantity, price, img.getOriginalFilename());
        this.productRepository.save(newProduct);
        return "redirect:/";
    }

    @GetMapping("products/update")
    public String updateProduct(
            @RequestParam Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Double price
    ){
        Optional<Product> pOpt = this.productRepository.findById(id);
        if(pOpt.isEmpty()) return "redirect:/";
        Product p = pOpt.get();
        if(name != null && !name.isEmpty()) {
            p.setName(name);
        }
        if(quantity != null) {
            p.setQuantity(quantity);
        }
        if(price != null) {
            p.setPrice(price);
        }
        this.productRepository.save(p);
        return "redirect:/";
    }

    @GetMapping("products/buy")
    @ResponseBody
    public Order buyProduct(
            @RequestParam Long id,
            @RequestParam Integer quantity,
            Authentication authentication
    ){
        String email = authentication.getName();
        User currentUser = this.userRepository.findByEmail(email);
        Optional<Product> productOpt = this.productRepository.findById(id);
        if(productOpt.isEmpty()) return null;

        Product product = productOpt.get();
        if(quantity > product.getQuantity()) return null;
        Double cost = quantity * product.getPrice();
        if(currentUser.getMoney() < cost) return null;

        product.setQuantity(product.getQuantity() - quantity);
        this.productRepository.save(product);

        currentUser.setMoney(currentUser.getMoney() - cost);
        this.userRepository.save(currentUser);

        User admin = this.userRepository.findByRole("ADMIN");
        admin.setMoney(admin.getMoney() + cost);
        this.userRepository.save(admin);

        Order order = new Order(currentUser, product, quantity, new Date());
        return this.orderRepository.save(order);
    }
}
