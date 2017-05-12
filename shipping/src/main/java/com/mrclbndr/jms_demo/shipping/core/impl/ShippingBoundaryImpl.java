package com.mrclbndr.jms_demo.shipping.core.impl;

import com.mrclbndr.jms_demo.shipping.core.api.ShippingBoundary;
import com.mrclbndr.jms_demo.shipping.domain.Order;
import com.mrclbndr.jms_demo.shipping.adpater.messaging.api.CustomerNotifier;
import com.mrclbndr.jms_demo.shipping.domain.ShippingState;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class ShippingBoundaryImpl implements ShippingBoundary {
    @Inject
    private CustomerNotifier customerNotifier;

    @Override
    public void prepareShipping(Order order) {
        customerNotifier.shippingStateChanged(order.getOrderId(), ShippingState.SHIPPING_ISSUED);
    }
}
