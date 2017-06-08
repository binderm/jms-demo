package com.mrclbndr.jms_demo.shopping.adapter.messaging.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;
import com.mrclbndr.jms_demo.shopping.domain.Order;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSContext;

@Named
@Dependent
public class QueueOrderSender implements OrderSender {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/NewOrdersQueue")
    private Destination newOrders;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void send(Order order) {
        try {
            String json = toJson(order);
            jmsContext.createProducer().send(newOrders, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String toJson(Order order) throws JsonProcessingException {
        return objectMapper.writeValueAsString(order);
    }
}
