package com.mrclbndr.jms_demo.shopping.domain;

public enum MessageType {
    TEXT_MESSAGE("TextMessage"),
    MAP_MESSAGE("MapMessage"),
    STREAM_MESSAGE("StreamMessage");

    private final String label;

    MessageType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
