package com.mrclbndr.jms_demo.billing.adapter.messaging.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.billing.adapter.messaging.api.CustomerNotifier;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;

@Stateless
public class CustomerNotifierImpl implements CustomerNotifier {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/CustomerNotifications")
    private Destination customerNotifications;

    @Override
    public void billAvailable(String orderId) {
        try {
            Message notification = jmsContext.createMessage();
            notification.setStringProperty("orderId", orderId);
            jmsContext.createProducer()
                    .setProperty("notificationType", "BILL_AVAILABLE")
                    .send(customerNotifications, notification);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
