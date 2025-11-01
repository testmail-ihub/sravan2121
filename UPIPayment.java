public class UPIPayment extends Payment {
    private String upiId;

    public UPIPayment(double amount, String currency, String upiId) {
        super(amount, currency);
        this.upiId = upiId;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing UPI payment of " + amount + " " + currency + " for UPI ID: " + upiId);
        // Add actual UPI processing logic here
    }
}