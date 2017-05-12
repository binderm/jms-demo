package com.mrclbndr.jms_demo.shopping.adapter.messaging.impl;

import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shopping.domain.ShippingState;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationLookup",
                propertyValue = "jms/CustomerNotifications"
        ),
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"
        ),
        @ActivationConfigProperty(
                propertyName = "messageSelector",
                propertyValue = "notificationType = 'SHIPPING_STATE_CHANGED'"
        )
})
public class ShippingStateChangedListener implements MessageListener {
    @Inject
    private OrderBoundary orderBoundary;

    @Override
    public void onMessage(Message message) {
        try {
            String orderId = message.getStringProperty("orderId");
            String shippingStateString = message.getStringProperty("updatedShippingState");
            ShippingState shippingState = ShippingState.valueOf(shippingStateString);
            orderBoundary.changeShippingState(orderId, shippingState);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
