package com.mrclbndr.jms_demo.shipping.adpater.messaging.impl;

import com.mrclbndr.jms_demo.shipping.core.api.ShippingBoundary;
import com.mrclbndr.jms_demo.shipping.domain.Order;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

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
                propertyValue = "prepareShippingOfNewOrder"
        )
})
public class NewOrderListener implements MessageListener {
    @Inject
    private OrderConverter orderConverter;

    @Inject
    private ShippingBoundary shippingBoundary;

    @Override
    public void onMessage(Message message) {
        Order order = orderConverter.toOrder(message);
        shippingBoundary.prepareShipping(order);
    }
}
