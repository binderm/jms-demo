package com.mrclbndr.jms_demo.billing.core.impl;

import com.mrclbndr.jms_demo.billing.core.api.BillBoundary;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CreateBillForNewOrderJob {
    @Inject
    private BillBoundary billBoundary;

    @Schedule(hour = "*", minute = "*", second = "0/2")
    public void createBillForNewOrder() {
        billBoundary.createBill();
    }
}
