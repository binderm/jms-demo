package com.mrclbndr.jms_demo.synchronous.core.impl;

import com.mrclbndr.jms_demo.synchronous.adapter.messaging.api.OrderReceiver;
import com.mrclbndr.jms_demo.synchronous.adapter.messaging.api.OrderSender;
import com.mrclbndr.jms_demo.synchronous.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.synchronous.domain.Order;
import com.mrclbndr.jms_demo.synchronous.domain.ReceiverConfiguration;
import com.mrclbndr.jms_demo.synchronous.domain.SenderConfiguration;

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

    @Inject
    private OrderReceiver orderReceiver;

    @Override
    public List<Order> generateAndSendNewOrders(int orderCount, SenderConfiguration configuration) {
        List<Order> orders = generateOrders(orderCount);
        for (Order order : orders) {
            orderSender.send(configuration, order);
        }
        return orders;
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
    public List<Order> receiveNewOrders(ReceiverConfiguration configuration) {
        return orderReceiver.receive(configuration);
    }
}
