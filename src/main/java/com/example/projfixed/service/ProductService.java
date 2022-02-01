package com.example.projfixed.service;

import com.example.projfixed.db.ProductRepository;
import com.example.projfixed.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceInterface {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Product save(Product p) {
        return this.repository.save(p);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.repository.findById(id);
    }
}
