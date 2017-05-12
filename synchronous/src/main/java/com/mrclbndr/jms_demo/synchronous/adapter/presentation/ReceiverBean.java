package com.mrclbndr.jms_demo.synchronous.adapter.presentation;

import com.mrclbndr.jms_demo.synchronous.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.synchronous.domain.Order;
import com.mrclbndr.jms_demo.synchronous.domain.ReceiverConfiguration;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Named("receiver")
@ViewScoped
public class ReceiverBean implements Serializable {
    private static final long serialVersionUID = 3942288729085687629L;

    @Inject
    private OrderBoundary orderBoundary;

    private final ReceiverConfiguration configuration = new ReceiverConfiguration();
    private final List<Order> receivedOrders = new LinkedList<>();

    public ReceiverConfiguration getConfiguration() {
        return configuration;
    }

    public List<Order> getReceivedOrders() {
        return receivedOrders;
    }

    public void receiveNewOrders() {
        List<Order> newOrders = orderBoundary.receiveNewOrders(configuration);
        receivedOrders.addAll(0, newOrders);
    }

    public void clearReceivedOrders() {
        receivedOrders.clear();
    }
}
