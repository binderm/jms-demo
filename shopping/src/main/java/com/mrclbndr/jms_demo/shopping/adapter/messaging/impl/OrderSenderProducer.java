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
    @Produces
    @ConfigurationDependent
    public OrderSender createOrderSender(@New QueueOrderSender queueOrderSender,
                                         @New TopicOrderSender topicOrderSender) {
        String shopping = System.getProperty("shopping");
        switch (shopping) {
            default:
                System.out.println("Destination unknown. Using queue.");
            case "queue":
                return queueOrderSender;
            case "topic":
                return topicOrderSender;
        }
    }
}