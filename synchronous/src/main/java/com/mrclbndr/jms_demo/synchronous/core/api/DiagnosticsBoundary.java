package com.mrclbndr.jms_demo.synchronous.core.api;

import com.mrclbndr.jms_demo.synchronous.domain.SystemStatus;

import java.util.List;

public interface DiagnosticsBoundary {
    List<String> getDeadMessages();

    SystemStatus getSystemStatus();
}
