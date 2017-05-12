package com.mrclbndr.jms_demo.shopping.core.api;

import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.SenderConfiguration;

import java.util.List;

public interface OrderBoundary {
    List<Order> simulateOrders(int orderCount, SenderConfiguration configuration);
}
