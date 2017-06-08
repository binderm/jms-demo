package com.mrclbndr.jms_demo.shipping.core.impl;

import com.mrclbndr.jms_demo.shipping.core.api.ShippingBoundary;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PrepareShippingForNewOrderJob {
    @Inject
    private ShippingBoundary shippingBoundary;

    @Schedule(hour = "*", minute = "*", second = "0/4")
    public void prepareShippingForNewOrder() {
        shippingBoundary.prepareShipping();
    }
}
