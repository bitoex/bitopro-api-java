package com.bitoex.bitopro.java.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Balance {

    private final BigDecimal amount;
    private final BigDecimal available;
    private final String currency;
    private final BigDecimal stake;

    @JsonCreator
    public Balance(BigDecimal amount, BigDecimal available, String currency, BigDecimal stake) {
        this.amount = amount;
        this.available = available;
        this.currency = currency;
        this.stake = stake;
    }

    /**
     * Get total amount for the currency.
     * @return total amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Get available amount for the currency, the amount usable.
     * @return available amount
     */
    public BigDecimal getAvailable() {
        return available;
    }

    /**
     * Get the currency symbol for the amount.
     * @return currency symbol
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Get the staked amount for the currency.
     * @return staked amount
     */
    public BigDecimal getStake() {
        return stake;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
