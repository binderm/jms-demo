package com.mrclbndr.jms_demo.billing.core.impl;

import com.mrclbndr.jms_demo.billing.adapter.messaging.api.CustomerNotifier;
import com.mrclbndr.jms_demo.billing.adapter.messaging.api.OrderReceiver;
import com.mrclbndr.jms_demo.billing.adapter.messaging.api.ConfigurationDependent;
import com.mrclbndr.jms_demo.billing.core.api.BillBoundary;
import com.mrclbndr.jms_demo.billing.domain.Order;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class BillBoundaryImpl implements BillBoundary {
    @Inject
    @ConfigurationDependent
    private OrderReceiver orderReceiver;

    @Inject
    private CustomerNotifier customerNotifier;

    @Schedule(hour = "*", minute = "*", second = "0/10")
    @Override
    public void createBill() {
        orderReceiver.nextOrder()
                .ifPresent(this::createBill);
    }

    @Override
    public void createBill(Order order) {
        String orderId = order.getOrderId();
        System.out.printf("Create bill for order %s%n", orderId);
        customerNotifier.billAvailable(orderId);
    }
}
