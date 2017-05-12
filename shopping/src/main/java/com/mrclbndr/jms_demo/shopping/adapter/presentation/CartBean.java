package com.mrclbndr.jms_demo.shopping.adapter.presentation;

import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shopping.domain.Destination;
import com.mrclbndr.jms_demo.shopping.domain.MessageType;
import com.mrclbndr.jms_demo.shopping.domain.Order;
import com.mrclbndr.jms_demo.shopping.domain.SenderConfiguration;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Named("cart")
@ViewScoped
public class CartBean implements Serializable {
    private static final long serialVersionUID = -2259480930743279507L;

    @Inject
    private OrderBoundary orderBoundary;

    private final SenderConfiguration configuration;
    private final List<Order> sentOrders;
    private int orderCount;

    public CartBean() {
        this.orderCount = 1;
        this.configuration = new SenderConfiguration();
        this.sentOrders = new LinkedList<>();
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
        return sentOrders;
    }

    public void simulateCheckouts() {
        List<Order> sentOrders = orderBoundary.simulateOrders(orderCount, configuration);
        this.sentOrders.addAll(0, sentOrders);
    }
}
