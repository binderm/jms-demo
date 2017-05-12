package com.mrclbndr.jms_demo.shopping.adapter.persistence.api;

import com.mrclbndr.jms_demo.shopping.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void add(Order order);

    List<Order> findAll();

    Optional<Order> findById(String orderId);

    void update(Order order);
}
