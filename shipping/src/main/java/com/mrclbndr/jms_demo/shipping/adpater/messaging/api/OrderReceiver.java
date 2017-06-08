package com.mrclbndr.jms_demo.shipping.adpater.messaging.api;

import com.mrclbndr.jms_demo.shipping.domain.Order;

import java.util.Optional;

public interface OrderReceiver {
    Optional<Order> nextOrder();
}
