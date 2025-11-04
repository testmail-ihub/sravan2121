package com.example;

import java.math.BigDecimal;

/**
 * Concrete implementation of Payment for credit card transactions.
 * Demonstrates inheritance by extending Payment and polymorphism through processPayment implementation.
 */
public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    
    /**
     * Constructor for CreditCardPayment
     * @param amount The payment amount
     * @param currency The currency code
     * @param cardNumber The credit card number
     * @param cardHolderName The name on the card
     * @param expiryDate The card expiry date (MM/YY format)
     * @param cvv The card verification value
     */
    public CreditCardPayment(BigDecimal amount, String currency, String cardNumber, 
                           String cardHolderName, String expiryDate, String cvv) {
        super(amount, currency); // Call parent constructor
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
    
    /**
     * Implementation of abstract processPayment method for credit card processing
     * @return String describing the credit card payment processing result
     */
    @Override
    public String processPayment() {
        // Simulate credit card processing logic
        if (validateCard()) {
            return String.format("Credit Card Payment Processed Successfully! " +
                               "Card ending in %s, Amount: %s %s, Cardholder: %s",
                               getLastFourDigits(), getAmount(), getCurrency(), cardHolderName);
        } else {
            return "Credit Card Payment Failed: Invalid card details";
        }
    }
    
    /**
     * Validates credit card details (simplified validation)
     * @return boolean indicating if card is valid
     */
    private boolean validateCard() {
        return cardNumber != null && cardNumber.length() >= 13 && cardNumber.length() <= 19 &&
               cardHolderName != null && !cardHolderName.trim().isEmpty() &&
               expiryDate != null && expiryDate.matches("\\d{2}/\\d{2}") &&
               cvv != null && cvv.matches("\\d{3,4}");
    }
    
    /**
     * Gets the last four digits of the card for display purposes
     * @return String with last four digits
     */
    private String getLastFourDigits() {
        if (cardNumber != null && cardNumber.length() >= 4) {
            return cardNumber.substring(cardNumber.length() - 4);
        }
        return "****";
    }
    
    /**
     * Override getPaymentDetails to include credit card specific information
     * @return String with enhanced payment details
     */
    @Override
    public String getPaymentDetails() {
        return String.format("%s, Payment Method: Credit Card (**** **** **** %s), Cardholder: %s",
                           super.getPaymentDetails(), getLastFourDigits(), cardHolderName);
    }
    
    // Getters and setters for credit card specific fields
    public String getCardHolderName() {
        return cardHolderName;
    }
    
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}