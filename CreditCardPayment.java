public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(double amount, String currency, String cardNumber, String cardHolderName) {
        super(amount, currency);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing credit card payment of " + amount + " " + currency + " for card ending in " + cardNumber.substring(cardNumber.length() - 4));
        // Add actual credit card processing logic here
    }
}