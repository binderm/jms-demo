package com.mrclbndr.jms_demo.shipping.adpater.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.shipping.domain.Order;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
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
