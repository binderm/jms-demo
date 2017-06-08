package com.mrclbndr.jms_demo.billing.adapter.messaging.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.billing.adapter.messaging.api.OrderReceiver;
import com.mrclbndr.jms_demo.billing.domain.Order;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import java.io.IOException;
import java.util.Optional;

@Named
@Dependent
public class TopicOrderReceiver implements OrderReceiver {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/NewOrdersTopic")
    private Destination newOrders;

    @Override
    public Optional<Order> nextOrder() {
        try (JMSConsumer consumer = jmsContext.createConsumer(newOrders)) {
            String json = consumer.receiveBody(String.class, 500L);
            if (json != null) {
                return Optional.of(new ObjectMapper().readValue(json, Order.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
