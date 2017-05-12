package com.mrclbndr.jms_demo.shipping.adpater.messaging.impl;

import com.mrclbndr.jms_demo.shipping.adpater.messaging.api.CustomerNotifier;
import com.mrclbndr.jms_demo.shipping.domain.ShippingState;

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
    public void shippingStateChanged(String orderId, ShippingState updatedShippingState) {
        try {
            Message notification = jmsContext.createMessage();
            notification.setStringProperty("orderId", orderId);
            notification.setStringProperty("updatedShippingState", updatedShippingState.name());
            jmsContext.createProducer()
                    .setProperty("notificationType", "SHIPPING_STATE_CHANGED")
                    .send(customerNotifications, notification);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
