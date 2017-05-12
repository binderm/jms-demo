package com.mrclbndr.jms_demo.synchronous.adapter.messaging.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.synchronous.adapter.messaging.api.OrderReceiver;
import com.mrclbndr.jms_demo.synchronous.domain.Order;
import com.mrclbndr.jms_demo.synchronous.domain.ReceiverConfiguration;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Stateless
public class OrderReceiverImpl implements OrderReceiver {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/NewOrdersQueue")
    private Queue newOrdersQueue;

    @Resource(lookup = "jms/NewOrdersTopic")
    private Topic newOrdersTopic;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Order> receive(ReceiverConfiguration configuration) {
        List<Order> receivedOrders = new LinkedList<>();

        try (JMSConsumer consumer = consumerForConfiguration(configuration)) {
            boolean ordersAvailable = true;
            for (int i = 0; ordersAvailable && i < configuration.getOrderCount(); i++) {
                Optional<Order> order = receiveOrder(consumer, configuration);
                order.ifPresent(receivedOrders::add);
                ordersAvailable = order.isPresent();
            }
        }

        return receivedOrders;
    }

    private JMSConsumer consumerForConfiguration(ReceiverConfiguration configuration) {
        switch (configuration.getDestination()) {
            case NEW_ORDERS_QUEUE:
            default:
                return consumerForQueue();
            case NEW_ORDERS_TOPIC:
                return consumerForTopic(configuration);
        }
    }

    private JMSConsumer consumerForQueue() {
        return jmsContext.createConsumer(newOrdersQueue);
    }

    private JMSConsumer consumerForTopic(ReceiverConfiguration configuration) {
        if (configuration.isDurable() && configuration.isShared()) {
            return jmsContext.createSharedDurableConsumer(newOrdersTopic, configuration.getSubscriptionName());
        }
        if (configuration.isDurable()) {
            return jmsContext.createDurableConsumer(newOrdersTopic, configuration.getSubscriptionName());
        }
        if (configuration.isShared()) {
            return jmsContext.createSharedConsumer(newOrdersTopic, configuration.getSubscriptionName());
        }
        return jmsContext.createConsumer(newOrdersTopic);
    }

    private Optional<Order> receiveOrder(JMSConsumer consumer, ReceiverConfiguration configuration) {
        Message message = configuration.getTimeout() == 0
                ? consumer.receiveNoWait()
                : consumer.receive(configuration.getTimeout());
        if (message == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(toOrder(message));
    }

    private Order toOrder(Message message) {
        try {
            if (message instanceof TextMessage) {
                return toOrder((TextMessage) message);
            } else if (message instanceof ObjectMessage) {
                return toOrder((ObjectMessage) message);
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
        return objectMapper.readValue(json, Order.class);
    }

    private Order toOrder(ObjectMessage message) throws JMSException {
        return (Order) message.getObject();
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
