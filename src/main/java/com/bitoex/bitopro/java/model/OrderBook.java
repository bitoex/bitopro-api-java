package com.bitoex.bitopro.java.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class OrderBook {

    private final List<Entry> asks;
    private final List<Entry> bids;

    @JsonCreator
    public OrderBook(List<Entry> asks, List<Entry> bids) {
        this.asks = asks;
        this.bids = bids;
    }

    public List<Entry> getAsks() {
        return asks;
    }

    public List<Entry> getBids() {
        return bids;
    }

    public static class Entry {

        private final BigDecimal amount;
        private final int count;
        private final BigDecimal price;
        private final BigDecimal total;

        @JsonCreator
        public Entry(BigDecimal amount, int count, BigDecimal price, BigDecimal total) {

            this.amount = amount;
            this.count = count;
            this.price = price;
            this.total = total;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public int getCount() {
            return count;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public BigDecimal getTotal() {
            return total;
        }
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
