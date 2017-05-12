package com.mrclbndr.jms_demo.shipping.core.api;

import com.mrclbndr.jms_demo.shipping.domain.Order;
import com.mrclbndr.jms_demo.shipping.domain.ReceiverConfiguration;

import java.util.List;

public interface OrderBoundary {
    List<Order> prepareShipping(int orderCount, ReceiverConfiguration configuration);
}
