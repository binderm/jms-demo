package com.mrclbndr.jms_demo.synchronous.adapter.messaging.api;

import com.mrclbndr.jms_demo.synchronous.domain.Order;
import com.mrclbndr.jms_demo.synchronous.domain.ReceiverConfiguration;

import java.util.List;

public interface OrderReceiver {
    List<Order> receive(ReceiverConfiguration configuration);
}
