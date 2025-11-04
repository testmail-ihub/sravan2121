package com.example;

import java.math.BigDecimal;

/**
 * Abstract base class for all payment types.
 * Demonstrates inheritance and polymorphism in a payment system.
 */
public abstract class Payment {
    protected BigDecimal amount;
    protected String currency;
    
    /**
     * Constructor to initialize payment with amount and currency
     * @param amount The payment amount
     * @param currency The currency code (e.g., "USD", "EUR")
     */
    public Payment(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }
    
    /**
     * Abstract method to be implemented by concrete payment classes
     * @return String describing the payment processing result
     */
    public abstract String processPayment();
    
    /**
     * Get the payment amount
     * @return BigDecimal amount
     */
    public BigDecimal getAmount() {
        return amount;
    }
    
    /**
     * Set the payment amount
     * @param amount The payment amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    /**
     * Get the currency
     * @return String currency code
     */
    public String getCurrency() {
        return currency;
    }
    
    /**
     * Set the currency
     * @param currency The currency code
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    /**
     * Common method to display payment details
     * @return String representation of payment details
     */
    public String getPaymentDetails() {
        return String.format("Amount: %s %s", amount, currency);
    }
}