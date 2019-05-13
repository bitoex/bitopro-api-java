package com.bitoex.bitopro.java.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class Trade {

    private final BigDecimal amount;
    private final BigDecimal price;
    private final boolean isBuyer;
    private final long timestamp;

    @JsonCreator
    public Trade(BigDecimal amount, BigDecimal price, boolean isBuyer, long timestamp) {
        this.amount = amount;
        this.price = price;
        this.isBuyer = isBuyer;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isBuyer() {
        return isBuyer;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
