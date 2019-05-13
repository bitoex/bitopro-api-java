package com.bitoex.bitopro.java.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class Ticker {

    private final BigDecimal high24hr;
    private final boolean isBuyer;
    private final BigDecimal lastPrice;
    private final BigDecimal low24hr;
    private final String pair;
    private final BigDecimal priceChange24hr;
    private final BigDecimal volume24hr;

    @JsonCreator
    public Ticker(BigDecimal high24hr, boolean isBuyer, BigDecimal lastPrice, BigDecimal low24hr, String pair, BigDecimal priceChange24hr, BigDecimal volume24hr) {
        this.high24hr = high24hr;
        this.isBuyer = isBuyer;
        this.lastPrice = lastPrice;
        this.low24hr = low24hr;
        this.pair = pair;
        this.priceChange24hr = priceChange24hr;
        this.volume24hr = volume24hr;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public BigDecimal getLow24hr() {
        return low24hr;
    }

    public BigDecimal getHigh24hr() {
        return high24hr;
    }

    public BigDecimal getVolume24hr() {
        return volume24hr;
    }

    public boolean isBuyer() {
        return isBuyer;
    }

    public String getPair() {
        return pair;
    }

    public BigDecimal getPriceChange24hr() {
        return priceChange24hr;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
