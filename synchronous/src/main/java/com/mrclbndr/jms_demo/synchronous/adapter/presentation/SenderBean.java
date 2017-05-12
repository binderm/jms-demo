package com.mrclbndr.jms_demo.synchronous.adapter.presentation;

import com.mrclbndr.jms_demo.synchronous.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.synchronous.domain.Order;
import com.mrclbndr.jms_demo.synchronous.domain.SenderConfiguration;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Named("sender")
@ViewScoped
public class SenderBean implements Serializable {
    private static final long serialVersionUID = 7126971370834138308L;

    @Inject
    private OrderBoundary orderBoundary;

    private final SenderConfiguration configuration = new SenderConfiguration();
    private int orderCount = 1;
    private final List<Order> sentOrders = new LinkedList<>();

    public SenderConfiguration getConfiguration() {
        return configuration;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public List<Order> getSentOrders() {
        return sentOrders;
    }

    public void generateAndSendNewOrders() {
        List<Order> newOrders = orderBoundary.generateAndSendNewOrders(orderCount, configuration);
        sentOrders.addAll(0, newOrders);
    }

    public void clearSentOrders() {
        sentOrders.clear();
    }
}
