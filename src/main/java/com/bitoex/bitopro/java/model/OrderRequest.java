package com.bitoex.bitopro.java.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OrderRequest {

    private OrderAction action;
    private BigDecimal amount;
    private BigDecimal price;
    private OrderType type;
    private long timestamp;

    public OrderAction getAction() {
        return action;
    }

    public void setAction(OrderAction action) {
        this.action = action;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
