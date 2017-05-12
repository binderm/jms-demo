package com.mrclbndr.jms_demo.shopping.core.api;

import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.SenderConfiguration;
import com.mrclbndr.jms_demo.shopping.domain.ShippingState;

import java.util.List;

public interface OrderBoundary {
    List<Order> getAllOrders();

    void simulateOrders(int orderCount, SenderConfiguration configuration);

    void billAvailable(String orderId);

    void changeShippingState(String orderId, ShippingState shippingState);
}
