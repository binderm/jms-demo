package com.mrclbndr.jms_demo.shipping.adpater.messaging.impl;

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
            if (message instanceof TextMessage) {
                return toOrder((TextMessage) message);
            } else if (message instanceof MapMessage) {
                return toOrder((MapMessage) message);
            } else if (message instanceof StreamMessage) {
                return toOrder((StreamMessage) message);
            }
        } catch (JMSException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private Order toOrder(TextMessage message) throws JMSException, IOException {
        String json = message.getText();
//        String json = message.getBody(String.class);
        return objectMapper.readValue(json, Order.class);
    }

    private Order toOrder(MapMessage message) throws JMSException {
        Order order = new Order();
        order.setOrderId(message.getString("orderId"));
        order.setPresent(message.getBoolean("present"));
        order.setShippingAddress(message.getString("shippingAddress"));
        return order;
    }

    private Order toOrder(StreamMessage message) throws JMSException {
        Order order = new Order();
        order.setOrderId(message.readString());
        order.setPresent(message.readBoolean());
        order.setShippingAddress(message.readString());
        return order;
    }
}
