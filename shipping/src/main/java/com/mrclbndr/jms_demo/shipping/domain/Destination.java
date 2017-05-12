package com.mrclbndr.jms_demo.shipping.domain;

public enum Destination {
    NEW_ORDERS_QUEUE("NewOrdersQueue"),
    NEW_ORDERS_TOPIC("NewOrdersTopic");

    private final String label;

    Destination(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
