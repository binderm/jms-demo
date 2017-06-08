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
    private static final String PROP_DESTINATION_TYPE = "jms_demo.destinationType";
    private static final String DESTINATION_TYPE_QUEUE = "queue";
    private static final String DESTINATION_TYPE_TOPIC = "topic";
    private static final String PROP_SUBSCRIPTION_DURABLE = "jms_demo.subscription.durable";
    private static final String PROP_SUBSCRIPTION_SHARED = "jms_demo.subscription.shared";

    @Produces
    @ConfigurationDependent
    public OrderReceiver<OrderType> createOrderReceiver(@New QueueOrderReceiver queueOrderReceiver,
                                                        @New TopicOrderReceiver topicOrderReceiver,
                                                        @New DurableTopicOrderReceiver durableTopicOrderReceiver,
                                                        @New SharedTopicOrderReceiver sharedTopicOrderReceiver,
                                                        @New SharedDurableTopicOrderReceiver sharedDurableTopicOrderReceiver) {
        String destinationType = System.getProperty(PROP_DESTINATION_TYPE, DESTINATION_TYPE_QUEUE);
        boolean durableSubscription = Boolean.parseBoolean(System.getProperty(PROP_SUBSCRIPTION_DURABLE));
        boolean sharedSubscription = Boolean.parseBoolean(System.getProperty(PROP_SUBSCRIPTION_SHARED));

        switch (destinationType) {
            default:
                System.err.printf("Destination type '%s' unknown. Using default value '%s' instead.",
                        destinationType, DESTINATION_TYPE_QUEUE);
            case DESTINATION_TYPE_QUEUE:
                return queueOrderReceiver;
            case DESTINATION_TYPE_TOPIC:
                return durableSubscription && sharedSubscription ? sharedDurableTopicOrderReceiver
                        : durableSubscription ? durableTopicOrderReceiver
                        : sharedSubscription ? sharedTopicOrderReceiver
                        : topicOrderReceiver;
        }
    }
}
