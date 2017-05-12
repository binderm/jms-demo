package com.mrclbndr.jms_demo.synchronous.adapter.messaging.api;

import com.mrclbndr.jms_demo.synchronous.domain.Order;
import com.mrclbndr.jms_demo.synchronous.domain.SenderConfiguration;

public interface OrderSender {
    void send(SenderConfiguration configuration, Order order);
}
