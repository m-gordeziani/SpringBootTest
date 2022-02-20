package com.example.projfixed.service;

import com.example.projfixed.db.ProductRepository;
import com.example.projfixed.model.Order;
import com.example.projfixed.model.Product;
import com.example.projfixed.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceInterface {

    private final String pathToProductImages = System.getProperty("user.dir")+"/src/main/resources/static/products/";
    private final String pathToCompiledProductImages = System.getProperty("user.dir")+"/target/classes/static/products/";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product save(Product p) {
        return this.productRepository.save(p);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Product updateProduct(Long id, String name, Integer quantity, Double price) {
        Optional<Product> pOpt = this.productRepository.findById(id);
        if(pOpt.isEmpty()) return null;
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
        return this.productRepository.save(p);
    }

    @Override
    public Order buyProduct(Long id, Integer quantity, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = this.userService.findByEmail(email);
        Optional<Product> productOpt = this.productRepository.findById(id);
        if(productOpt.isEmpty()) return null;

        Product product = productOpt.get();
        if(quantity > product.getQuantity()) return null;
        Double cost = quantity * product.getPrice();
        if(currentUser.getMoney() < cost) return null;

        product.setQuantity(product.getQuantity() - quantity);
        this.productRepository.save(product);

        currentUser.setMoney(currentUser.getMoney() - cost);
        this.userService.save(currentUser);

        User admin = this.userService.findByRole("ADMIN");
        admin.setMoney(admin.getMoney() + cost);
        this.userService.save(admin);

        Order order = new Order(currentUser, product, quantity, new Date());
        return this.orderService.save(order);
    }

    @Override
    public Product addProduct(String name, Integer quantity, Double price, MultipartFile img) {
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
        return this.productRepository.save(newProduct);
    }


}
