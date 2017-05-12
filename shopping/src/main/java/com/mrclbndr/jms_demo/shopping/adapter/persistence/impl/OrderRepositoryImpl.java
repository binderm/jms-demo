package com.mrclbndr.jms_demo.shopping.adapter.persistence.impl;

import com.mrclbndr.jms_demo.shopping.adapter.persistence.api.OrderRepository;
import com.mrclbndr.jms_demo.shopping.domain.Order;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.mrclbndr.jms_demo.shopping.domain.Order.FIND_ALL_ORDERS;

@Stateless
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Order order) {
        entityManager.persist(order);
    }

    @Override
    public List<Order> findAll() {
        return entityManager.createNamedQuery(FIND_ALL_ORDERS, Order.class)
                .getResultList();
    }

    @Override
    public Optional<Order> findById(String orderId) {
        Order order = entityManager.find(Order.class, orderId);
        return Optional.ofNullable(order);
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }
}
