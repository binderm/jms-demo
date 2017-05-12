package com.mrclbndr.jms_demo.billing.core.impl;

import com.mrclbndr.jms_demo.billing.core.api.BillBoundary;
import com.mrclbndr.jms_demo.billing.domain.Order;
import com.mrclbndr.jms_demo.billing.adapter.messaging.api.CustomerNotifier;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.Random;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class BillBoundaryImpl implements BillBoundary {
    @Inject
    private CustomerNotifier customerNotifier;

    @Override
    public void createBill(Order order) {
        randomDelay(2000);
        customerNotifier.billAvailable(order.getOrderId());
    }

    private void randomDelay(int maxMs) {
        Random random = new Random();
        int delay = random.nextInt(maxMs);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ignore) {
        }
    }
}
