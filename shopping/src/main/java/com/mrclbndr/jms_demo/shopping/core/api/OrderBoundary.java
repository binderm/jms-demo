package com.mrclbndr.jms_demo.shopping.core.api;

import com.mrclbndr.jms_demo.shopping.domain.ShippingState;

public interface OrderBoundary {
    void billAvailable(String orderId);

    void changeShippingState(String orderId, ShippingState shippingState);
}
