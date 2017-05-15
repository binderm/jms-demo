package com.mrclbndr.jms_demo.synchronous.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Address {
    private final String street;

    public Address(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("street", street)
                .toString();
    }
}
