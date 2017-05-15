package com.mrclbndr.jms_demo.synchronous.adapter.messaging.impl;

import com.mrclbndr.jms_demo.synchronous.adapter.messaging.api.AddressValidator;
import com.mrclbndr.jms_demo.synchronous.domain.Address;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TemporaryQueue;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.NOT_SUPPORTED;

@Stateless
public class AddressValidatorImpl implements AddressValidator {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/ValidationRequests")
    private Destination requestDestination;

    @Override
    @Transactional(NOT_SUPPORTED)
    public Address validate(Address toBeValidated) {
        try {
            TemporaryQueue replyDestination = jmsContext.createTemporaryQueue();

            jmsContext.createProducer()
                    .setJMSReplyTo(replyDestination)
                    .send(requestDestination, toJson(toBeValidated));

            Address validated;
            try (JMSConsumer consumer = jmsContext.createConsumer(replyDestination)) {
                validated = toAddress(consumer.receiveBody(String.class));
            }
            replyDestination.delete();
            return validated;
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String toJson(Address address) {
        return address.getStreet();
    }

    private static Address toAddress(String json) {
        return new Address(json);
    }
}
