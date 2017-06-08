package com.mrclbndr.jms_demo.billing.adapter.messaging.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.billing.adapter.messaging.api.OrderReceiver;
import com.mrclbndr.jms_demo.billing.domain.Order;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import java.io.IOException;
import java.util.Optional;

public abstract class AbstractOrderReceiver implements OrderReceiver {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/NewOrdersTopic")
    private Topic newOrders;

    @Override
    public Optional<Order> nextOrder() {
        try (JMSConsumer consumer = createConsumer(jmsContext)) {
            String json = consumer.receiveBody(String.class, 500L);
            if (json != null) {
                return Optional.of(new ObjectMapper().readValue(json, Order.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    abstract JMSConsumer createConsumer(JMSContext jmsContext);
}
