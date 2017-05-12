package com.mrclbndr.jms_demo.shipping.core.api;

import com.mrclbndr.jms_demo.shipping.domain.Order;

public interface ShippingBoundary {
    void prepareShipping(Order order);
}
