package com.mrclbndr.jms_demo.shipping.adpater.presentation;

import com.mrclbndr.jms_demo.shipping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shipping.domain.Destination;
import com.mrclbndr.jms_demo.shipping.domain.Order;
import com.mrclbndr.jms_demo.shipping.domain.ReceiverConfiguration;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Named("shipping")
@ViewScoped
public class PrepareShippingBean implements Serializable {
    private static final long serialVersionUID = -8703295537807242811L;

    @Inject
    private OrderBoundary orderBoundary;

    private int orderCount;

    private final ReceiverConfiguration configuration;

    private final List<Order> receivedOrders;

    public PrepareShippingBean() {
        this.orderCount = 1;
        this.configuration = new ReceiverConfiguration();
        this.receivedOrders = new LinkedList<>();
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

    public Map<String, Long> getAvailableTimeouts() {
        return Collections.unmodifiableMap(new HashMap<String, Long>() {
            {
                put("No wait", 0L);
                put("1s", 1000L);
                put("2s", 2000L);
                put("4s", 4000L);
                put("8s", 8000L);
            }
        });
    }

    public ReceiverConfiguration getConfiguration() {
        return configuration;
    }

    public List<Order> getReceivedOrders() {
        return receivedOrders;
    }

    public void prepareShippings() {
        List<Order> receivedOrders = orderBoundary.prepareShipping(orderCount, configuration);
        this.receivedOrders.addAll(0, receivedOrders);
    }
}
