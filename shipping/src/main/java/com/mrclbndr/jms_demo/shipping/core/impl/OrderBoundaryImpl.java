package com.mrclbndr.jms_demo.shipping.core.impl;

import com.mrclbndr.jms_demo.shipping.core.api.OrderBoundary;
import com.mrclbndr.jms_demo.shipping.domain.Order;
import com.mrclbndr.jms_demo.shipping.domain.ReceiverConfiguration;
import com.mrclbndr.jms_demo.shipping.adpater.messaging.api.OrderReceiveException;
import com.mrclbndr.jms_demo.shipping.adpater.messaging.api.OrderReceiver;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class OrderBoundaryImpl implements OrderBoundary {
    @Inject
    private OrderReceiver orderReceiver;

    @Override
    public List<Order> prepareShipping(int orderCount, ReceiverConfiguration configuration) {
        List<Order> receivedOrders = new LinkedList<>();

        boolean orderAvailable = true;
        for (int i = 0; orderAvailable && i < orderCount; i++) {
            try {
                Optional<Order> order = orderReceiver.getNextOrder(configuration);
                orderAvailable = order.isPresent();
                if (orderAvailable) {
                    receivedOrders.add(order.get());
                }
            } catch (OrderReceiveException e) {
                System.err.printf("Failed to receive order%n");
                e.printStackTrace();
            }
        }

        return receivedOrders;
    }
}
