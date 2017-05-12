package com.mrclbndr.jms_demo.shopping.core.impl;

import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;
import com.mrclbndr.jms_demo.shopping.adapter.persistence.api.OrderRepository;
import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.ShippingState;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class OrderBoundaryImpl implements OrderBoundary {
    private static final int ORDER_COUNT = 5;

    @Inject
    private OrderSender orderSender;

    @Inject
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Schedule(hour = "*", minute = "*", second = "0,30")
    public void generateAndSendOrders() {
        List<Order> orders = generateOrders(ORDER_COUNT);
        for (Order order : orders) {
            orderSender.send(order);
            orderRepository.add(order);
        }
    }

    private List<Order> generateOrders(int orderCount) {
        List<Order> orders = new LinkedList<>();

        Random random = new Random();
        int idPrefix = random.nextInt(Integer.MAX_VALUE);
        for (int i = 1; i <= orderCount; i++) {
            Order order = new Order();
            order.setOrderId(String.format("%08X-%04d", idPrefix, i));
            orders.add(order);
        }

        return orders;
    }

    @Override
    public void billAvailable(String orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setBillAvailable(true);
            orderRepository.update(order);
        });
    }

    @Override
    public void changeShippingState(String orderId, ShippingState shippingState) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setShippingState(shippingState);
            orderRepository.update(order);
        });
    }
}
