package com.mrclbndr.jms_demo.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

import static com.mrclbndr.jms_demo.shopping.domain.Order.FIND_ALL_ORDERS;

@Entity
@Table(name = "ORDERS")
@NamedQueries({
        @NamedQuery(name = FIND_ALL_ORDERS, query = "select o from Order o")
})
public class Order {
    public static final String FIND_ALL_ORDERS = "findAllOrders";

    @Id
    @JsonProperty("orderId")
    private String orderId;

    @Transient
    @JsonProperty("items")
    private List<OrderItem> items = new ArrayList<>();

    @JsonProperty("present")
    private boolean present;

    @JsonProperty("shippingAddress")
    private String shippingAddress;

    @JsonProperty("billingAddress")
    private String billingAddress;

    @JsonIgnore
    private boolean billAvailable;

    @Enumerated
    @JsonIgnore
    private ShippingState shippingState = ShippingState.IN_PREPARATION;

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

    public boolean isBillAvailable() {
        return billAvailable;
    }

    public void setBillAvailable(boolean billAvailable) {
        this.billAvailable = billAvailable;
    }

    public ShippingState getShippingState() {
        return shippingState;
    }

    public void setShippingState(ShippingState shippingState) {
        this.shippingState = shippingState;
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
                .append(billAvailable, order.billAvailable)
                .append(shippingState, order.shippingState)
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
                .append(billAvailable)
                .append(shippingState)
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
                .append("billAvailable", billAvailable)
                .append("shippingState", shippingState)
                .toString();
    }
}
