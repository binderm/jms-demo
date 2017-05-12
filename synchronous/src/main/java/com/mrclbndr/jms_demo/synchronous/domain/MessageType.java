package com.mrclbndr.jms_demo.synchronous.domain;

public enum MessageType implements Selectable {
    TEXT_MESSAGE("TextMessage"),
    OBJECT_MESSAGE("ObjectMessage"),
    MAP_MESSAGE("MapMessage"),
    STREAM_MESSAGE("StreamMessage"),
    BYTE_MESSAGE("ByteMessage");

    private final String label;

    MessageType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
