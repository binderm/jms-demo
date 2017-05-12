package com.mrclbndr.jms_demo.synchronous.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = 4554676359524889865L;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("items")
    private List<OrderItem> items = new ArrayList<>();

    @JsonProperty("present")
    private boolean present;

    @JsonProperty("shippingAddress")
    private String shippingAddress;

    @JsonProperty("billingAddress")
    private String billingAddress;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @JsonIgnore
    public int getItemCount() {
        return items.size();
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return new EqualsBuilder()
                .append(present, order.present)
                .append(orderId, order.orderId)
                .append(items, order.items)
                .append(shippingAddress, order.shippingAddress)
                .append(billingAddress, order.billingAddress)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orderId)
                .append(items)
                .append(present)
                .append(shippingAddress)
                .append(billingAddress)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("orderId", orderId)
                .append("items", items)
                .append("present", present)
                .append("shippingAddress", shippingAddress)
                .append("billingAddress", billingAddress)
                .toString();
    }
}
