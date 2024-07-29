// Step 2: Define Strategy Interface
interface PaymentStrategy {
    void pay(double amount);
}

// Step 3: Implement Concrete Strategies

// Credit Card Payment Implementation
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing credit card payment of $" + amount +
                " for card number " + cardNumber + " (Card Holder: " + cardHolderName + ")");
    }
}

// PayPal Payment Implementation
class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment of $" + amount +
                " for email " + email);
    }
}

// Step 4: Implement Context Class
class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void executePayment(double amount) {
        paymentStrategy.pay(amount);
    }
}

// Step 5: Test the Strategy Implementation
 class StrategyPatternExample {
    public static void main(String[] args) {
        // Create PaymentContext
        PaymentContext paymentContext = new PaymentContext();

        // Create Credit Card Payment Strategy
        PaymentStrategy creditCardPayment = new CreditCardPayment("1234-5678-9876-5432", "John Doe");
        paymentContext.setPaymentStrategy(creditCardPayment);
        System.out.println("Paying using Credit Card:");
        paymentContext.executePayment(100.0);

        System.out.println();

        // Create PayPal Payment Strategy
        PaymentStrategy payPalPayment = new PayPalPayment("johndoe@example.com");
        paymentContext.setPaymentStrategy(payPalPayment);
        System.out.println("Paying using PayPal:");
        paymentContext.executePayment(150.0);
    }
}
