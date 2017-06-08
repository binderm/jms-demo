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
import java.util.List;
import java.util.Random;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class OrderBoundaryImpl implements OrderBoundary {
    @Inject
    private OrderSender orderSender;

    @Inject
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Schedule(hour = "*", minute = "*", second = "*")
    public void generateAndSendOrders() {
        Order order = generateOrder();
        orderSender.send(order);
        System.out.printf("Sent order %s%n", order.getOrderId());
        orderRepository.add(order);
    }

    private Order generateOrder() {
        Random random = new Random();
        int idPrefix = random.nextInt(Integer.MAX_VALUE);
        Order order = new Order();
        order.setOrderId(String.format("%08X", idPrefix));
        return order;
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
