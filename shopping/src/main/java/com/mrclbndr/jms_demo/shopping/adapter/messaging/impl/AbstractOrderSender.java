package com.mrclbndr.jms_demo.shopping.adapter.messaging.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;
import com.mrclbndr.jms_demo.shopping.domain.Order;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;

public abstract class AbstractOrderSender implements OrderSender {
    @Inject
    private JMSContext jmsContext;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void send(Order order) {
        try {
            String json = toJson(order);
            jmsContext.createProducer().send(newOrdersDestination(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String toJson(Order order) throws JsonProcessingException {
        return objectMapper.writeValueAsString(order);
    }

    abstract Destination newOrdersDestination();
}
