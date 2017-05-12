package com.mrclbndr.jms_demo.shopping.adapter.messaging.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.shopping.domain.MessageType;
import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.OrderItem;
import com.mrclbndr.jms_demo.shopping.domain.SenderConfiguration;
import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.StreamMessage;

@Stateless
public class OrderSenderImpl implements OrderSender {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/NewOrdersQueue")
    private Destination newOrdersQueue;

    @Resource(lookup = "jms/NewOrdersTopic")
    private Destination newOrdersTopic;

    @Resource(lookup = "jms/NewOrders")
    private Destination newOrders;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendOrder(SenderConfiguration configuration, Order order) {
        Message message = toMessage(configuration.getMessageType(), order);
        Destination destination = destinationForConfiguration(configuration);
        jmsContext.createProducer()
                .send(destination, message);
    }

    private Destination destinationForConfiguration(SenderConfiguration configuration) {
        switch (configuration.getDestination()) {
            case NEW_ORDERS_QUEUE:
                return newOrdersQueue;
            case NEW_ORDERS_TOPIC:
                return newOrdersTopic;
            default:
                return newOrders;
        }
    }

    public Message toMessage(MessageType messageType, Order order) {
        try {
            switch (messageType) {
                case TEXT_MESSAGE:
                default:
                    return toTextMessage(order);
                case MAP_MESSAGE:
                    return toMapMessage(order);
                case STREAM_MESSAGE:
                    return toStreamMessage(order);
            }
        } catch (JsonProcessingException | JMSException e) {
            return null;
        }
    }

    private Message toTextMessage(Order order) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(order);
        return jmsContext.createTextMessage(json);
    }

    private Message toMapMessage(Order order) throws JMSException {
        MapMessage message = jmsContext.createMapMessage();
        message.setString("orderId", order.getOrderId());
        message.setBoolean("present", order.isPresent());
        message.setString("shippingAddress", order.getShippingAddress());
        message.setString("billingAddress", order.getBillingAddress());
        for (OrderItem item : order.getItems()) {
            message.setInt("orderItem_" + item.getProductId(), item.getQuantity());
        }
        return message;
    }

    private Message toStreamMessage(Order order) throws JMSException {
        StreamMessage message = jmsContext.createStreamMessage();
        message.writeString(order.getOrderId());
        message.writeBoolean(order.isPresent());
        message.writeString(order.getShippingAddress());
        message.writeString(order.getBillingAddress());
        message.writeInt(order.getItemCount());
        for (OrderItem item : order.getItems()) {
            message.writeString(item.getProductId());
            message.writeInt(item.getQuantity());
        }
        return message;
    }
}
