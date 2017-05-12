package com.mrclbndr.jms_demo.shopping.adapter.presentation;

import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shopping.domain.Destination;
import com.mrclbndr.jms_demo.shopping.domain.MessageType;
import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.SenderConfiguration;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Named("cart")
@RequestScoped
public class CartBean {
    @Inject
    private OrderBoundary orderBoundary;

    private final SenderConfiguration configuration;
    private int orderCount;
    private List<Order> sentOrders;

    public CartBean() {
        this.orderCount = 1;
        this.configuration = new SenderConfiguration();
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public List<Destination> getAvailableDestinations() {
        return Arrays.asList(Destination.values());
    }

    public List<MessageType> getAvailableMessageTypes() {
        return Arrays.asList(MessageType.values());
    }

    public SenderConfiguration getConfiguration() {
        return configuration;
    }

    public List<Order> getSentOrders() {
        if (sentOrders == null) {
            sentOrders = orderBoundary.getAllOrders();
        }
        return sentOrders;
    }

    public void simulateCheckouts() {
        orderBoundary.simulateOrders(orderCount, configuration);
    }
}
