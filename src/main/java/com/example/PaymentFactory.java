package com.example;

import java.math.BigDecimal;

/**
 * Factory class for creating Payment objects.
 * Demonstrates the Factory design pattern and polymorphism by returning appropriate Payment subclasses.
 */
public class PaymentFactory {
    
    /**
     * Enum defining supported payment types
     */
    public enum PaymentType {
        CREDIT_CARD,
        UPI
    }
    
    /**
     * Creates a Payment object based on the specified type and parameters
     * @param paymentType The type of payment to create
     * @param amount The payment amount
     * @param currency The currency code
     * @param paymentDetails Variable arguments containing payment-specific details
     * @return Payment object (CreditCardPayment or UPIPayment)
     * @throws IllegalArgumentException if payment type is unsupported or parameters are invalid
     */
    public static Payment createPayment(PaymentType paymentType, BigDecimal amount, 
                                      String currency, String... paymentDetails) {
        
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive and non-null");
        }
        
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
        
        switch (paymentType) {
            case CREDIT_CARD:
                return createCreditCardPayment(amount, currency, paymentDetails);
            case UPI:
                return createUPIPayment(amount, currency, paymentDetails);
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }
    
    /**
     * Creates a CreditCardPayment object
     * @param amount The payment amount
     * @param currency The currency code
     * @param details Array containing [cardNumber, cardHolderName, expiryDate, cvv]
     * @return CreditCardPayment object
     */
    private static CreditCardPayment createCreditCardPayment(BigDecimal amount, String currency, 
                                                           String[] details) {
        if (details.length < 4) {
            throw new IllegalArgumentException(
                "Credit card payment requires: cardNumber, cardHolderName, expiryDate, cvv");
        }
        
        return new CreditCardPayment(amount, currency, details[0], details[1], details[2], details[3]);
    }
    
    /**
     * Creates a UPIPayment object
     * @param amount The payment amount
     * @param currency The currency code
     * @param details Array containing [upiId, bankName, transactionPin]
     * @return UPIPayment object
     */
    private static UPIPayment createUPIPayment(BigDecimal amount, String currency, String[] details) {
        if (details.length < 3) {
            throw new IllegalArgumentException(
                "UPI payment requires: upiId, bankName, transactionPin");
        }
        
        return new UPIPayment(amount, currency, details[0], details[1], details[2]);
    }
    
    /**
     * Convenience method to create a Credit Card payment
     * @param amount The payment amount
     * @param currency The currency code
     * @param cardNumber The credit card number
     * @param cardHolderName The cardholder name
     * @param expiryDate The expiry date
     * @param cvv The CVV
     * @return CreditCardPayment object
     */
    public static CreditCardPayment createCreditCardPayment(BigDecimal amount, String currency,
                                                           String cardNumber, String cardHolderName,
                                                           String expiryDate, String cvv) {
        return new CreditCardPayment(amount, currency, cardNumber, cardHolderName, expiryDate, cvv);
    }
    
    /**
     * Convenience method to create a UPI payment
     * @param amount The payment amount
     * @param currency The currency code
     * @param upiId The UPI ID
     * @param bankName The bank name
     * @param transactionPin The transaction PIN
     * @return UPIPayment object
     */
    public static UPIPayment createUPIPayment(BigDecimal amount, String currency,
                                             String upiId, String bankName, String transactionPin) {
        return new UPIPayment(amount, currency, upiId, bankName, transactionPin);
    }
}