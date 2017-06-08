package com.mrclbndr.jms_demo.billing.adapter.messaging.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.billing.domain.Order;
import com.mrclbndr.jms_demo.billing.domain.OrderItem;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
        order.setBillingAddress(message.getString("billingAddress"));
        return order;
    }

    private Order toOrder(StreamMessage message) throws JMSException {
        Order order = new Order();
        order.setOrderId(message.readString());
        order.setPresent(message.readBoolean());
        String shippingAddress = message.readString();
        order.setBillingAddress(message.readString());
        int itemCount = message.readInt();
        List<OrderItem> items = new LinkedList<>();
        for (int i = 0; i < itemCount; i++) {
            OrderItem item = new OrderItem();
            item.setProductId(message.readString());
            item.setQuantity(message.readInt());
            items.add(item);
        }
        order.setItems(items);
        return order;
    }
}
