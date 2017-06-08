package com.mrclbndr.jms_demo.billing.adapter.messaging.impl;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;

@Named
@Dependent
public class DurableTopicOrderReceiver extends AbstractOrderReceiver {
    @Resource(lookup = "jms/NewOrdersTopic")
    private Topic newOrders;

    @Override
    JMSConsumer createConsumer(JMSContext jmsContext) {
        return jmsContext.createDurableConsumer(newOrders, "billing");
    }
}
