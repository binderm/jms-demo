package com.mrclbndr.jms_demo.shopping.adapter.messaging.listener;

import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;

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
                propertyValue = "notificationType = 'BILL_AVAILABLE'"
        )
})
public class BillAvailableListener implements MessageListener {
    @Inject
    private OrderBoundary orderBoundary;

    @Override
    public void onMessage(Message message) {
        try {
            String orderId = message.getStringProperty("orderId");
            orderBoundary.billAvailable(orderId);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
