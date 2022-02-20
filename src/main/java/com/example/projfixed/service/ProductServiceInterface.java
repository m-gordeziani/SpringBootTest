package com.example.projfixed.service;

import com.example.projfixed.model.Order;
import com.example.projfixed.model.Product;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {

    List<Product> findAll();

    Product save(Product p);

    Optional<Product> findById(Long id);

    Product updateProduct(Long id, String name, Integer quantity, Double price);

    Order buyProduct(Long id, Integer quantity, Authentication authentication);

    Product addProduct(String name, Integer quantity, Double price, MultipartFile img);
}
