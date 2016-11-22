package com.wifiesta.restaurant.model;

import java.math.BigDecimal;

public class Price {

    private String currency;
    private BigDecimal amount;

    public Price(String currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    // Just for the serializer
    public Price() {
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return this.currency + " " + this.amount.toPlainString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.amount == null) ? 0 : this.amount.hashCode());
        result = prime * result + ((this.currency == null) ? 0 : this.currency.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Price other = (Price) obj;
        if (this.amount == null) {
            if (other.amount != null) {
                return false;
            }
        } else if (!this.amount.equals(other.amount)) {
            return false;
        }
        if (this.currency == null) {
            if (other.currency != null) {
                return false;
            }
        } else if (!this.currency.equals(other.currency)) {
            return false;
        }
        return true;
    }

}
