public class PaymentTest {
    public static void main(String[] args) {
        // Test CreditCardPayment
        Payment creditCardPayment = PaymentFactory.getPayment("CreditCard", 100.0, "USD", "1234567890123456", "John Doe");
        creditCardPayment.processPayment();

        // Test UPIPayment
        Payment upiPayment = PaymentFactory.getPayment("UPI", 50.0, "INR", "john.doe@upi");
        upiPayment.processPayment();

        // Demonstrate polymorphism
        Payment[] payments = new Payment[2];
        payments[0] = PaymentFactory.getPayment("CreditCard", 200.0, "EUR", "9876543210987654", "Jane Smith");
        payments[1] = PaymentFactory.getPayment("UPI", 75.0, "INR", "jane.smith@upi");

        for (Payment payment : payments) {
            payment.processPayment();
        }
    }
}