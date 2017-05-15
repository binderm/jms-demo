package com.mrclbndr.jms_demo.synchronous.core.impl;

import com.mrclbndr.jms_demo.synchronous.adapter.messaging.api.DmqInspector;
import com.mrclbndr.jms_demo.synchronous.core.api.DiagnosticsBoundary;
import com.mrclbndr.jms_demo.synchronous.domain.SystemStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class DiagnosticsBoundaryImpl implements DiagnosticsBoundary {
    @Inject
    private DmqInspector dmqInspector;

    @Override
    public List<String> getDeadMessages() {
        return dmqInspector.getDeadMessages();
    }

    @Override
    public SystemStatus getSystemStatus() {
        return new SystemStatus();
    }
}
