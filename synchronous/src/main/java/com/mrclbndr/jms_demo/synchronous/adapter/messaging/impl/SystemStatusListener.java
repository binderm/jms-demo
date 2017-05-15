package com.mrclbndr.jms_demo.synchronous.adapter.messaging.impl;

import com.mrclbndr.jms_demo.synchronous.core.api.AddressBoundary;
import com.mrclbndr.jms_demo.synchronous.core.api.DiagnosticsBoundary;
import com.mrclbndr.jms_demo.synchronous.domain.Address;
import com.mrclbndr.jms_demo.synchronous.domain.SystemStatus;

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
public class SystemStatusListener implements MessageListener {
    @Inject
    private DiagnosticsBoundary diagnosticsBoundary;

    @Inject
    private JMSContext jmsContext;

    @Override
    public void onMessage(Message statusRequest) {
        try {
            SystemStatus systemStatus = diagnosticsBoundary.getSystemStatus();

            String requestId = statusRequest.getJMSMessageID();
            Destination replyDestination = statusRequest.getJMSReplyTo();
            jmsContext.createProducer()
                    .setJMSCorrelationID(requestId)
                    .send(replyDestination, systemStatus);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
