package com.bitoex.bitopro.java.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Order {

    private final OrderAction action;
    private final BigDecimal avgExecutionPrice;
    private final BigDecimal bitoFee;
    private final BigDecimal executedAmount;
    private final BigDecimal fee;
    private final String feeSymbol;
    private final String id;
    private final BigDecimal originalAmount;
    private final String pair;
    private final BigDecimal price;
    private final BigDecimal remainingAmount;
    private final OrderStatus status;
    private final long timestamp;
    private final OrderType type;

    @JsonCreator
    public Order(OrderAction action,
                 BigDecimal avgExecutionPrice,
                 BigDecimal bitoFee,
                 BigDecimal executedAmount,
                 BigDecimal fee,
                 String feeSymbol,
                 String id,
                 BigDecimal originalAmount,
                 String pair,
                 BigDecimal price,
                 BigDecimal remainingAmount,
                 OrderStatus status,
                 long timestamp,
                 OrderType type) {
        this.action = action;
        this.avgExecutionPrice = avgExecutionPrice;
        this.bitoFee = bitoFee;
        this.executedAmount = executedAmount;
        this.fee = fee;
        this.feeSymbol = feeSymbol;
        this.id = id;
        this.originalAmount = originalAmount;
        this.pair = pair;
        this.price = price;
        this.remainingAmount = remainingAmount;
        this.status = status;
        this.timestamp = timestamp;
        this.type = type;
    }

    public OrderAction getAction() {
        return action;
    }

    public BigDecimal getAvgExecutionPrice() {
        return avgExecutionPrice;
    }

    public BigDecimal getBitoFee() {
        return bitoFee;
    }

    public BigDecimal getExecutedAmount() {
        return executedAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public String getFeeSymbol() {
        return feeSymbol;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public String getPair() {
        return pair;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public OrderType getType() {
        return type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
