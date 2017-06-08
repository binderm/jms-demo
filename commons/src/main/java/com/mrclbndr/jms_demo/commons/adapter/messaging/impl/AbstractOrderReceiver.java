package com.mrclbndr.jms_demo.commons.adapter.messaging.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.commons.adapter.messaging.api.OrderReceiver;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import java.io.IOException;
import java.util.Optional;

public abstract class AbstractOrderReceiver<OrderType> implements OrderReceiver<OrderType> {
    @Inject
    private JMSContext jmsContext;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Optional<OrderType> nextOrder(Class<OrderType> orderClazz) {
        try (JMSConsumer consumer = createConsumer(jmsContext)) {
            String json = consumer.receiveBody(String.class, 500L);
            if (json != null) {
                OrderType order = objectMapper.readValue(json, orderClazz);
                return Optional.of(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    abstract JMSConsumer createConsumer(JMSContext jmsContext);
}
