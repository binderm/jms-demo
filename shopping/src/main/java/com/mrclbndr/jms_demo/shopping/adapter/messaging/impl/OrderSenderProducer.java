package com.mrclbndr.jms_demo.shopping.adapter.messaging.impl;

import com.mrclbndr.jms_demo.commons.adapter.messaging.impl.Configuration;
import com.mrclbndr.jms_demo.commons.adapter.messaging.impl.DestinationType;
import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.ConfigurationDependent;
import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Named
@Dependent
public class OrderSenderProducer {
    @Produces
    @ConfigurationDependent
    public OrderSender createOrderSender(Configuration configuration,
                                         @New QueueOrderSender queueOrderSender,
                                         @New TopicOrderSender topicOrderSender) {
        DestinationType destinationType = configuration.destinationType();

        switch (destinationType) {
            default:
            case QUEUE:
                return queueOrderSender;
            case TOPIC:
                return topicOrderSender;
        }
    }
}