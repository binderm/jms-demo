package com.mrclbndr.jms_demo.shopping.adapter.messaging.impl;

import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.ConfigurationDependent;
import com.mrclbndr.jms_demo.shopping.adapter.messaging.api.OrderSender;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Named
@Dependent
public class OrderSenderProducer {
    private static final String PROP_DESTINATION_TYPE = "jms_demo.destinationType";
    private static final String DESTINATION_TYPE_QUEUE = "queue";
    private static final String DESTINATION_TYPE_TOPIC = "topic";

    @Produces
    @ConfigurationDependent
    public OrderSender createOrderSender(@New QueueOrderSender queueOrderSender,
                                         @New TopicOrderSender topicOrderSender) {
        String destinationType = System.getProperty(PROP_DESTINATION_TYPE, DESTINATION_TYPE_QUEUE);

        switch (destinationType) {
            default:
                System.err.printf("Destination type '%s' unknown. Using default value '%s' instead.",
                        destinationType, DESTINATION_TYPE_QUEUE);
            case "queue":
                return queueOrderSender;
            case "topic":
                return topicOrderSender;
        }
    }
}