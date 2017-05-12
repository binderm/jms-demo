package com.mrclbndr.jms_demo.shopping.adapter.presentation;

import com.mrclbndr.jms_demo.shopping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shopping.domain.Order;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("orderOverview")
@RequestScoped
public class OrderOverviewBean {
    @Inject
    private OrderBoundary orderBoundary;

    public List<Order> getOrders() {
        return orderBoundary.getAllOrders();
    }
}
