package com.mrclbndr.jms_demo.synchronous.adapter.messaging.api;

import java.util.List;

public interface DmqInspector {
    List<String> getDeadMessages();
}
