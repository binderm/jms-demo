package com.mrclbndr.jms_demo.shopping.adapter.messaging.api;

import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.SenderConfiguration;

public interface OrderSender {
    void sendOrder(SenderConfiguration configuration, Order order);
}
