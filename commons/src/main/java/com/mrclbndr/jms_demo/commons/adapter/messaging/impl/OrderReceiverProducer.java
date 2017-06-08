package com.mrclbndr.jms_demo.commons.adapter.messaging.impl;

import com.mrclbndr.jms_demo.billing.adapter.messaging.api.ConfigurationDependent;
import com.mrclbndr.jms_demo.commons.adapter.messaging.api.OrderReceiver;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Named
@Dependent
public class OrderReceiverProducer<OrderType> {
    @Produces
    @ConfigurationDependent
    public OrderReceiver<OrderType> createOrderReceiver(Configuration configuration,
                                                        @New QueueOrderReceiver queueOrderReceiver,
                                                        @New TopicOrderReceiver topicOrderReceiver,
                                                        @New DurableTopicOrderReceiver durableTopicOrderReceiver,
                                                        @New SharedTopicOrderReceiver sharedTopicOrderReceiver,
                                                        @New SharedDurableTopicOrderReceiver sharedDurableTopicOrderReceiver) {
        DestinationType destinationType = configuration.destinationType();
        boolean durableSubscription = configuration.isDurableSubscription();
        boolean sharedSubscription = configuration.isSharedSubscription();

        switch (destinationType) {
            default:
            case QUEUE:
                System.out.println("Receive from queue");
                return queueOrderReceiver;
            case TOPIC:
                if (durableSubscription && sharedSubscription) {
                    System.out.println("Receive from shared durable subscription");
                    return sharedDurableTopicOrderReceiver;
                }
                if (durableSubscription) {
                    System.out.println("Receive from non-shared durable subscription");
                    return durableTopicOrderReceiver;
                }
                if (sharedSubscription) {
                    System.out.println("Receive from shared non-durable subscription");
                    return sharedTopicOrderReceiver;
                }
                System.out.println("Receive from non-durable non-shared subscription");
                return topicOrderReceiver;
        }
    }
}
