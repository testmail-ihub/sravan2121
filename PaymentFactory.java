public class PaymentFactory {
    public static Payment getPayment(String type, double amount, String currency, String... details) {
        if (type.equalsIgnoreCase("CreditCard")) {
            return new CreditCardPayment(amount, currency, details[0], details[1]);
        } else if (type.equalsIgnoreCase("UPI")) {
            return new UPIPayment(amount, currency, details[0]);
        } else {
            throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }
}