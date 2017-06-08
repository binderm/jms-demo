package com.mrclbndr.jms_demo.billing.core.api;

import com.mrclbndr.jms_demo.billing.domain.Order;

public interface BillBoundary {
    void createBill(Order order);
}
