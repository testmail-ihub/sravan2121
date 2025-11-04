package com.example;

import java.math.BigDecimal;

/**
 * Test class demonstrating inheritance, polymorphism, and the Factory pattern
 * with the Payment system hierarchy.
 */
public class PaymentTest {
    
    public static void main(String[] args) {
        System.out.println("=== Payment System Polymorphism Demo ===");
        System.out.println();
        
        // Test 1: Direct object creation and polymorphism
        testDirectObjectCreation();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test 2: Factory pattern with polymorphism
        testFactoryPattern();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test 3: Polymorphic array processing
        testPolymorphicArray();
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    /**
     * Demonstrates direct object creation and polymorphic method calls
     */
    private static void testDirectObjectCreation() {
        System.out.println("TEST 1: Direct Object Creation & Polymorphism");
        System.out.println("-".repeat(45));
        
        // Create Credit Card Payment
        Payment creditCardPayment = new CreditCardPayment(
            new BigDecimal("150.75"),
            "USD",
            "4532123456789012",
            "John Doe",
            "12/25",
            "123"
        );
        
        // Create UPI Payment
        Payment upiPayment = new UPIPayment(
            new BigDecimal("89.50"),
            "INR",
            "john.doe@paytm",
            "HDFC Bank",
            "1234"
        );
        
        // Demonstrate polymorphism - same method calls, different implementations
        System.out.println("Credit Card Payment Details:");
        System.out.println(creditCardPayment.getPaymentDetails());
        System.out.println("Processing: " + creditCardPayment.processPayment());
        
        System.out.println("\nUPI Payment Details:");
        System.out.println(upiPayment.getPaymentDetails());
        System.out.println("Processing: " + upiPayment.processPayment());
    }
    
    /**
     * Demonstrates Factory pattern with polymorphism
     */
    private static void testFactoryPattern() {
        System.out.println("TEST 2: Factory Pattern & Polymorphism");
        System.out.println("-".repeat(40));
        
        try {
            // Create payments using factory - returns Payment interface
            Payment factoryCreatedCC = PaymentFactory.createPayment(
                PaymentFactory.PaymentType.CREDIT_CARD,
                new BigDecimal("299.99"),
                "EUR",
                "5555444433221111", "Jane Smith", "06/26", "456"
            );
            
            Payment factoryCreatedUPI = PaymentFactory.createPayment(
                PaymentFactory.PaymentType.UPI,
                new BigDecimal("75.25"),
                "INR",
                "jane.smith@gpay", "SBI Bank", "5678"
            );
            
            // Process payments polymorphically
            System.out.println("Factory-created Credit Card:");
            processPayment(factoryCreatedCC);
            
            System.out.println("\nFactory-created UPI:");
            processPayment(factoryCreatedUPI);
            
            // Test convenience methods
            System.out.println("\nUsing Convenience Factory Methods:");
            CreditCardPayment convenienceCC = PaymentFactory.createCreditCardPayment(
                new BigDecimal("500.00"), "USD", "4111111111111111", 
                "Alice Johnson", "03/27", "789"
            );
            
            UPIPayment convenienceUPI = PaymentFactory.createUPIPayment(
                new BigDecimal("120.00"), "INR", "alice@phonepe", 
                "ICICI Bank", "9876"
            );
            
            processPayment(convenienceCC);
            processPayment(convenienceUPI);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Factory Error: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates polymorphic processing with arrays
     */
    private static void testPolymorphicArray() {
        System.out.println("TEST 3: Polymorphic Array Processing");
        System.out.println("-".repeat(37));
        
        // Create array of Payment objects (polymorphic collection)
        Payment[] payments = {
            new CreditCardPayment(new BigDecimal("199.99"), "USD", 
                                "4000000000000002", "Bob Wilson", "09/25", "321"),
            new UPIPayment(new BigDecimal("50.00"), "INR", 
                          "bob@paytm", "Axis Bank", "4321"),
            PaymentFactory.createCreditCardPayment(new BigDecimal("750.50"), "EUR", 
                                                  "5200000000000007", "Carol Brown", "11/26", "654"),
            PaymentFactory.createUPIPayment(new BigDecimal("25.75"), "INR", 
                                           "carol@gpay", "PNB Bank", "8765")
        };
        
        // Process all payments polymorphically
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (int i = 0; i < payments.length; i++) {
            Payment payment = payments[i];
            System.out.println(String.format("Payment %d (%s):", i + 1, 
                             payment.getClass().getSimpleName()));
            System.out.println("  " + payment.getPaymentDetails());
            System.out.println("  " + payment.processPayment());
            
            // Add to total (simplified - assumes same currency for demo)
            if ("USD".equals(payment.getCurrency()) || "EUR".equals(payment.getCurrency())) {
                totalAmount = totalAmount.add(payment.getAmount());
            }
            System.out.println();
        }
        
        System.out.println("Total USD/EUR Payments Processed: $" + totalAmount);
        System.out.println("Number of Payments: " + payments.length);
    }
    
    /**
     * Helper method demonstrating polymorphic parameter usage
     * @param payment Any Payment implementation
     */
    private static void processPayment(Payment payment) {
        System.out.println("  Type: " + payment.getClass().getSimpleName());
        System.out.println("  Details: " + payment.getPaymentDetails());
        System.out.println("  Result: " + payment.processPayment());
    }
}