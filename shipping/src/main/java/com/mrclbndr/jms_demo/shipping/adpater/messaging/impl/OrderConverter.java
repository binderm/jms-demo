package com.mrclbndr.jms_demo.shipping.adpater.messaging.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.shipping.domain.Order;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import java.io.IOException;

@Stateless
public class OrderConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Order toOrder(Message message) {
        try {
            String json = message.getBody(String.class);
            return toOrder(json);
        } catch (JMSException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Order toOrder(String json) throws IOException {
        return objectMapper.readValue(json, Order.class);
    }
}
