package com.mrclbndr.jms_demo.billing.adapter.messaging.api;

import com.mrclbndr.jms_demo.billing.domain.Order;

import java.util.Optional;

public interface OrderReceiver {
    Optional<Order> nextOrder();
}
