package com.example.projfixed.service;

import com.example.projfixed.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {

    List<Product> findAll();

    Product save(Product p);

    Optional<Product> findById(Long id);
}
