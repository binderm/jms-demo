package com.mrclbndr.jms_demo.billing.adapter.messaging.impl;

import com.mrclbndr.jms_demo.billing.adapter.messaging.api.ConfigurationDependent;
import com.mrclbndr.jms_demo.billing.adapter.messaging.api.OrderReceiver;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Named
@Dependent
public class OrderReceiverProducer {
    @Produces
    @ConfigurationDependent
    private OrderReceiver createOrderReceiver(@New QueueOrderReceiver queueOrderReceiver,
                                              @New TopicOrderReceiver topicOrderReceiver,
                                              @New DurableTopicOrderReceiver durableTopicOrderReceiver,
                                              @New SharedTopicOrderReceiver sharedTopicOrderReceiver,
                                              @New SharedDurableTopicOrderReceiver sharedDurableTopicOrderReceiver) {
        String billing = System.getProperty("billing");
        switch (billing) {
            default:
                System.out.println("Destination unknown. Using queue.");
            case "queue":
                return queueOrderReceiver;
            case "topic":
                return topicOrderReceiver;
            case "durable":
                return durableTopicOrderReceiver;
            case "shared":
                return sharedTopicOrderReceiver;
            case "shared-durable":
                return sharedDurableTopicOrderReceiver;
        }
    }
}
