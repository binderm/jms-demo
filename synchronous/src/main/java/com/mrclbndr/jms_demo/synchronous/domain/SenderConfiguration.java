package com.mrclbndr.jms_demo.synchronous.domain;

public class SenderConfiguration {
    private Destination destination;
    private MessageType messageType;

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
