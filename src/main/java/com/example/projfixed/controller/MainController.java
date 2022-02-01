package com.example.projfixed.controller;

import com.example.projfixed.db.ProductRepository;
import com.example.projfixed.model.Product;
import com.example.projfixed.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(Model model, Authentication authentication){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

}
