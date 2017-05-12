package com.mrclbndr.jms_demo.billing.adapter.messaging.api;

public interface CustomerNotifier {
    void billAvailable(String orderId);
}
