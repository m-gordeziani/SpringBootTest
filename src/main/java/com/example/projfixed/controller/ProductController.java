package com.example.projfixed.controller;

import com.example.projfixed.model.Order;
import com.example.projfixed.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("products/add")
    public String addNewProduct(@RequestParam String name,
                                @RequestParam Integer quantity,
                                @RequestParam Double price,
                                @RequestParam MultipartFile img
    ) {
        this.productService.addProduct(name, quantity, price, img);
        return "redirect:/";
    }

    @GetMapping("products/update")
    public String updateProduct(
            @RequestParam Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Double price
    ){
        this.productService.updateProduct(id, name, quantity, price);
        return "redirect:/";
    }

    @GetMapping("products/buy")
    @ResponseBody
    public Order buyProduct(
            @RequestParam Long id,
            @RequestParam Integer quantity,
            Authentication authentication
    ){
        return this.productService.buyProduct(id, quantity, authentication);
    }
}
