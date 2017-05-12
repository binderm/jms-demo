package com.mrclbndr.jms_demo.synchronous.domain;

public enum Destination implements Selectable {
    NEW_ORDERS_QUEUE("NewOrdersQueue"),
    NEW_ORDERS_TOPIC("NewOrdersTopic");

    private final String label;

    Destination(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
