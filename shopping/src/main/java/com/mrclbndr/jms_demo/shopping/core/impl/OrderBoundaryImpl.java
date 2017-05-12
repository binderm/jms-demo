package com.mrclbndr.jms_demo.shopping.core.impl;

import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.SenderConfiguration;
import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;

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
    @Inject
    private OrderSender orderSender;

    @Override
    public List<Order> simulateOrders(int orderCount, SenderConfiguration configuration) {
        List<Order> orders = new LinkedList<>();

        String simulationId = generateSimulationId();
        int firstOrderId = 1;
        for (int orderId = firstOrderId; orderId <= orderCount; orderId++) {
            Order order = generateOrder(simulationId, orderId);
            orderSender.sendOrder(configuration, order);
            orders.add(order);
        }

        return orders;
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
}
