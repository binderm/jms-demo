package com.mrclbndr.jms_demo.shipping.core.impl;

import com.mrclbndr.jms_demo.billing.adapter.messaging.api.ConfigurationDependent;
import com.mrclbndr.jms_demo.commons.adapter.messaging.api.OrderReceiver;
import com.mrclbndr.jms_demo.shipping.adpater.messaging.api.CustomerNotifier;
import com.mrclbndr.jms_demo.shipping.core.api.ShippingBoundary;
import com.mrclbndr.jms_demo.shipping.domain.Order;
import com.mrclbndr.jms_demo.shipping.domain.ShippingState;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class ShippingBoundaryImpl implements ShippingBoundary {
    @Inject
    @ConfigurationDependent
    private OrderReceiver<Order> orderReceiver;

    @Inject
    private CustomerNotifier customerNotifier;

    @Schedule(hour = "*", minute = "*", second = "0/5")
    public void prepareShipping() {
        orderReceiver.nextOrder(Order.class)
                .ifPresent(this::prepareShipping);
    }

    @Override
    public void prepareShipping(Order order) {
        String orderId = order.getOrderId();
        System.out.printf("Prepare shipping for order %s%n", orderId);
        customerNotifier.shippingStateChanged(orderId, ShippingState.SHIPPING_ISSUED);
    }
}
