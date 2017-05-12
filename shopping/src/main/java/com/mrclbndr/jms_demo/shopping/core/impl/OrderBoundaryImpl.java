package com.mrclbndr.jms_demo.shopping.core.impl;

import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;
import com.mrclbndr.jms_demo.shopping.adapter.persistence.api.OrderRepository;
import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shopping.domain.ShippingState;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class OrderBoundaryImpl implements OrderBoundary {
    @Inject
    private OrderSender orderSender;

    @Inject
    private OrderRepository orderRepository;

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
