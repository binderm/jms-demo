package com.mrclbndr.jms_demo.shipping.core.impl;

import com.mrclbndr.jms_demo.shipping.core.api.ShippingBoundary;
import com.mrclbndr.jms_demo.shipping.domain.Order;
import com.mrclbndr.jms_demo.shipping.adpater.messaging.api.CustomerNotifier;
import com.mrclbndr.jms_demo.shipping.domain.ShippingState;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.Random;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class ShippingBoundaryImpl implements ShippingBoundary {
    @Inject
    private CustomerNotifier customerNotifier;

    @Override
    public void prepareShipping(Order order) {
        randomDelay(3000);
        customerNotifier.shippingStateChanged(order.getOrderId(), ShippingState.SHIPPING_ISSUED);
    }

    private void randomDelay(int maxMs) {
        Random random = new Random();
        int delay = random.nextInt(maxMs);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ignore) {
        }
    }
}
