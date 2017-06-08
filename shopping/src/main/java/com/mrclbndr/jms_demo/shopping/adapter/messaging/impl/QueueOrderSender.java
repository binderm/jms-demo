package com.mrclbndr.jms_demo.shopping.adapter.messaging.impl;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.jms.Destination;

@Named
@Dependent
public class QueueOrderSender extends AbstractOrderSender {
    @Resource(lookup = "jms/NewOrdersQueue")
    private Destination newOrders;

    @Override
    Destination newOrdersDestination() {
        return newOrders;
    }
}
