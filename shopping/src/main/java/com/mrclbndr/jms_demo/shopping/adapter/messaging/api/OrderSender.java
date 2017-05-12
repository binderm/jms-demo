package com.mrclbndr.jms_demo.shopping.adapter.messaging.api;

import com.mrclbndr.jms_demo.shopping.domain.Order;

public interface OrderSender {
    void sendOrder(Order order);
}
