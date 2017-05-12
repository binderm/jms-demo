package com.mrclbndr.jms_demo.shopping.core.impl;

import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;
import com.mrclbndr.jms_demo.shopping.adapter.persistence.api.OrderRepository;
import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.SenderConfiguration;
import com.mrclbndr.jms_demo.shopping.domain.ShippingState;

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

    @Override
    public void simulateOrders(int orderCount, SenderConfiguration configuration) {
        String simulationId = generateSimulationId();
        int firstOrderId = 1;
        for (int orderId = firstOrderId; orderId <= orderCount; orderId++) {
            Order order = generateOrder(simulationId, orderId);
            orderSender.sendOrder(configuration, order);
            orderRepository.add(order);
        }
    }

    private static String generateSimulationId() {
        Random random = new Random();
        int simulationId = random.nextInt(Integer.MAX_VALUE);
        return Integer.toHexString(simulationId);
    }

    private static Order generateOrder(String simulationId, int orderId) {
        Order order = new Order();
        order.setOrderId(simulationId + "-" + orderId);
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
