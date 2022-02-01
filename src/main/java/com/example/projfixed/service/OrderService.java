package com.example.projfixed.service;

import com.example.projfixed.db.OrderRepository;
import com.example.projfixed.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements OrderServiceInterface{

    @Autowired
    private OrderRepository repository;

    @Override
    public Order save(Order o) {
        return this.repository.save(o);
    }
}
