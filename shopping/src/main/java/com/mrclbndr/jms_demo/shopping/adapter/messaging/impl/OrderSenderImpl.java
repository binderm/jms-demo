package com.mrclbndr.jms_demo.shopping.adapter.messaging.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;

@Stateless
public class OrderSenderImpl implements OrderSender {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/NewOrders")
    private Destination newOrders;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendOrder(Order order) {
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
