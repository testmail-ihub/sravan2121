package com.example;

import java.math.BigDecimal;

/**
 * Concrete implementation of Payment for UPI (Unified Payments Interface) transactions.
 * Demonstrates inheritance by extending Payment and polymorphism through different processPayment implementation.
 */
public class UPIPayment extends Payment {
    private String upiId;
    private String bankName;
    private String transactionPin;
    
    /**
     * Constructor for UPIPayment
     * @param amount The payment amount
     * @param currency The currency code
     * @param upiId The UPI ID (e.g., user@paytm, user@gpay)
     * @param bankName The associated bank name
     * @param transactionPin The UPI transaction PIN
     */
    public UPIPayment(BigDecimal amount, String currency, String upiId, 
                     String bankName, String transactionPin) {
        super(amount, currency); // Call parent constructor
        this.upiId = upiId;
        this.bankName = bankName;
        this.transactionPin = transactionPin;
    }
    
    /**
     * Implementation of abstract processPayment method for UPI processing
     * @return String describing the UPI payment processing result
     */
    @Override
    public String processPayment() {
        // Simulate UPI processing logic
        if (validateUPI()) {
            return String.format("UPI Payment Processed Successfully! " +
                               "UPI ID: %s, Bank: %s, Amount: %s %s, Transaction ID: %s",
                               getMaskedUpiId(), bankName, getAmount(), getCurrency(), 
                               generateTransactionId());
        } else {
            return "UPI Payment Failed: Invalid UPI details or insufficient balance";
        }
    }
    
    /**
     * Validates UPI details (simplified validation)
     * @return boolean indicating if UPI details are valid
     */
    private boolean validateUPI() {
        return upiId != null && upiId.contains("@") && upiId.length() > 5 &&
               bankName != null && !bankName.trim().isEmpty() &&
               transactionPin != null && transactionPin.matches("\\d{4,6}") &&
               getAmount().compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Masks UPI ID for security purposes
     * @return String with masked UPI ID
     */
    private String getMaskedUpiId() {
        if (upiId != null && upiId.contains("@")) {
            String[] parts = upiId.split("@");
            String username = parts[0];
            String domain = parts[1];
            
            if (username.length() > 2) {
                String masked = username.substring(0, 2) + "***" + username.substring(username.length() - 1);
                return masked + "@" + domain;
            }
        }
        return "***@***";
    }
    
    /**
     * Generates a mock transaction ID for demonstration
     * @return String transaction ID
     */
    private String generateTransactionId() {
        return "UPI" + System.currentTimeMillis() % 1000000;
    }
    
    /**
     * Override getPaymentDetails to include UPI specific information
     * @return String with enhanced payment details
     */
    @Override
    public String getPaymentDetails() {
        return String.format("%s, Payment Method: UPI (%s), Bank: %s",
                           super.getPaymentDetails(), getMaskedUpiId(), bankName);
    }
    
    // Getters and setters for UPI specific fields
    public String getUpiId() {
        return upiId;
    }
    
    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}