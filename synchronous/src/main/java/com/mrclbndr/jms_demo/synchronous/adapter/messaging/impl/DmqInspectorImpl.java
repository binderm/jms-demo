package com.mrclbndr.jms_demo.synchronous.adapter.messaging.impl;

import com.mrclbndr.jms_demo.synchronous.adapter.messaging.api.DmqInspector;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.List;

@Stateless
public class DmqInspectorImpl implements DmqInspector {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/Dmq")
    private Queue dmq;

    @Override
    public List<String> getDeadMessages() {
        return null;
    }
}
