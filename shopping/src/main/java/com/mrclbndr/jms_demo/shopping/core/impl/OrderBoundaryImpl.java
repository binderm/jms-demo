package com.mrclbndr.jms_demo.shopping.core.impl;

import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.ConfigurationDependent;
import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;
import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.ShippingState;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class OrderBoundaryImpl implements OrderBoundary {
    @Inject
    private OrderIdGenerator orderIdGenerator;

    @Inject
    @ConfigurationDependent
    private OrderSender orderSender;

    @Schedule(hour = "*", minute = "*", second = "0/2")
    public void generateAndSendOrders() {
        Order order = generateOrder();
        orderSender.send(order);
        System.out.printf("Sent order '%s'%n", order.getOrderId());
    }

    private Order generateOrder() {
        Order order = new Order();
        String orderId = orderIdGenerator.generateOrderId();
        order.setOrderId(orderId);
        return order;
    }

    @Override
    public void billAvailable(String orderId) {
        System.out.printf("Bill available for order '%s'%n", orderId);
    }

    @Override
    public void changeShippingState(String orderId, ShippingState shippingState) {
        System.out.printf("Shipping state changed for order '%s': '%s'%n", orderId, shippingState);
    }
}
