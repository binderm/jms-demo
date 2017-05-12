package com.mrclbndr.jms_demo.shipping.adpater.messaging.api;

import com.mrclbndr.jms_demo.shipping.domain.ShippingState;

public interface CustomerNotifier {
    void shippingStateChanged(String orderId, ShippingState updatedShippingState);
}
