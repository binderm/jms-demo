package com.mrclbndr.jms_demo.synchronous.core.api;

import com.mrclbndr.jms_demo.synchronous.domain.Order;
import com.mrclbndr.jms_demo.synchronous.domain.ReceiverConfiguration;
import com.mrclbndr.jms_demo.synchronous.domain.SenderConfiguration;

import java.util.List;

public interface OrderBoundary {
    List<Order> generateAndSendNewOrders(int orderCount, SenderConfiguration configuration);

    List<Order> receiveNewOrders(ReceiverConfiguration configuration);
}
