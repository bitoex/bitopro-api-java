package com.bitoex.bitopro.java.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OrderResponse {

    private final OrderAction action;
    private final BigDecimal amount;
    private final String orderId;
    private final BigDecimal price;
    private final long timestamp;

    @JsonCreator
    public OrderResponse(String orderId, OrderAction action, BigDecimal amount, BigDecimal price, long timestamp) {

        this.action = action;
        this.amount = amount;
        this.orderId = orderId;
        this.price = price;
        this.timestamp = timestamp;
    }

    public OrderAction getAction() {
        return action;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
