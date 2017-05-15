package com.mrclbndr.jms_demo.synchronous.adapter.messaging.impl;

import com.mrclbndr.jms_demo.synchronous.adapter.messaging.api.SystemStatusRequestor;
import com.mrclbndr.jms_demo.synchronous.domain.Address;
import com.mrclbndr.jms_demo.synchronous.domain.SystemStatus;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TemporaryQueue;
import javax.transaction.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static javax.transaction.Transactional.TxType.NOT_SUPPORTED;

@Stateless
public class SystemStatusRequestorImpl implements SystemStatusRequestor {
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(30L);

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/SystemStatus")
    private Destination requestDestination;

    @Override
    @Transactional(NOT_SUPPORTED)
    public List<SystemStatus> getOverallStatus() {
        try {
            TemporaryQueue replyDestination = jmsContext.createTemporaryQueue();

            Message request = jmsContext.createMessage();
            jmsContext.createProducer()
                    .setJMSReplyTo(replyDestination)
                    .setTimeToLive(TIMEOUT)
                    .send(requestDestination, request);

            List<SystemStatus> overallStatus = new LinkedList<>();
            try (JMSConsumer consumer = jmsContext.createConsumer(replyDestination)) {
                SystemStatus systemStatus;
                do {
                    systemStatus = consumer.receiveBody(SystemStatus.class, TIMEOUT);
                    if (systemStatus != null) {
                        overallStatus.add(systemStatus);
                    }
                } while (systemStatus != null);
            }
            replyDestination.delete();
            return overallStatus;
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }
}
