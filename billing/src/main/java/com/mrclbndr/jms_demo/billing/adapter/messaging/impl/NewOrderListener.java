package com.mrclbndr.jms_demo.billing.adapter.messaging.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrclbndr.jms_demo.billing.core.api.BillBoundary;
import com.mrclbndr.jms_demo.billing.domain.Order;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationLookup",
                propertyValue = "jms/NewOrders"
        ),
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"
        ),
        @ActivationConfigProperty(
                propertyName = "subscriptionDurability",
                propertyValue = "Durable"
        ),
        @ActivationConfigProperty(
                propertyName = "subscriptionName",
                propertyValue = "createBillForNewOrder"
        )
})
public class NewOrderListener implements MessageListener {
    @Inject
    private OrderConverter orderConverter;

    @Inject
    private BillBoundary billBoundary;

    @Override
    public void onMessage(Message message) {
        try {
            String json = message.getBody(String.class);
            Order order = fromJson(json);
            billBoundary.createBill(order);
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }
    }

    private Order fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Order.class);
    }
}
