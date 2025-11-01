public abstract class Payment {
    protected double amount;
    protected String currency;

    public Payment(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public abstract void processPayment();
}