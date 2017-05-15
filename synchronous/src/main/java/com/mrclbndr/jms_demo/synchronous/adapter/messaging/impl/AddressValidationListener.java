package com.mrclbndr.jms_demo.synchronous.adapter.messaging.impl;

import com.mrclbndr.jms_demo.synchronous.core.api.AddressBoundary;
import com.mrclbndr.jms_demo.synchronous.domain.Address;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationLookup",
                propertyValue = "jms/ValidationRequests"
        ),
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"
        )
})
public class AddressValidationListener implements MessageListener {
    @Inject
    private AddressBoundary addressBoundary;

    @Inject
    private JMSContext jmsContext;

    @Override
    public void onMessage(Message validationRequest) {
        try {
            Address toBeValidated = toAddress(validationRequest.getBody(String.class));
            Address validated = addressBoundary.validate(toBeValidated);

            String requestId = validationRequest.getJMSMessageID();
            Destination replyDestination = validationRequest.getJMSReplyTo();
            jmsContext.createProducer()
                    .setJMSCorrelationID(requestId)
                    .send(replyDestination, toJson(validated));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static String toJson(Address address) {
        return address.getStreet();
    }

    private static Address toAddress(String json) {
        return new Address(json);
    }
}
