package com.mrclbndr.jms_demo.synchronous.adapter.messaging.api;

import com.mrclbndr.jms_demo.synchronous.domain.Address;
import com.mrclbndr.jms_demo.synchronous.domain.SystemStatus;

import java.util.List;

public interface SystemStatusRequestor {
    List<SystemStatus> getOverallStatus();
}
