package com.example.api;

import java.math.BigDecimal;

public abstract class Payment {
    protected BigDecimal amount;
    protected String currency;

    public Payment(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public abstract void processPayment();
}
