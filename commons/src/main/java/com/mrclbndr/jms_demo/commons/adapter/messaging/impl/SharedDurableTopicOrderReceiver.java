package com.mrclbndr.jms_demo.commons.adapter.messaging.impl;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;

@Named
@Dependent
public class SharedDurableTopicOrderReceiver extends AbstractOrderReceiver {
    @Resource(lookup = "jms/NewOrdersTopic")
    private Topic newOrders;

    @Override
    JMSConsumer createConsumer(JMSContext jmsContext) {
        return jmsContext.createSharedDurableConsumer(newOrders, "billing");
    }
}
