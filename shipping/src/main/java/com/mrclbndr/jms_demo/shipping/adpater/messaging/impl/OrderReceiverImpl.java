package com.mrclbndr.jms_demo.shipping.adpater.messaging.impl;

import com.mrclbndr.jms_demo.shipping.domain.Order;
import com.mrclbndr.jms_demo.shipping.domain.ReceiverConfiguration;
import com.mrclbndr.jms_demo.shipping.adpater.messaging.api.OrderReceiveException;
import com.mrclbndr.jms_demo.shipping.adpater.messaging.api.OrderReceiver;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;
import java.util.Optional;

@Stateless
public class OrderReceiverImpl implements OrderReceiver {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/NewOrdersQueue")
    private Queue newOrdersQueue;

    @Resource(lookup = "jms/NewOrdersTopic")
    private Topic newOrdersTopic;

    @Inject
    private OrderConverter orderConverter;

    @Override
    public Optional<Order> getNextOrder(ReceiverConfiguration configuration) throws OrderReceiveException {
        try (JMSConsumer consumer = consumerForConfiguration(configuration)) {
            Message message = configuration.getTimeout() == 0
                    ? consumer.receiveNoWait()
                    : consumer.receive(configuration.getTimeout());
            if (message == null) {
                return Optional.empty();
            }
            return Optional.ofNullable(orderConverter.toOrder(message));
        }
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
}
