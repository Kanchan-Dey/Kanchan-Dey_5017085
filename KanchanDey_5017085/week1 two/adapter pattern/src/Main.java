 class AdapterPatternExample {

    public static void main(String[] args) {
        PaymentProcessor processor1 = new PaypalAdapter();
        PaymentProcessor processor2 = new StripeAdapter();

        processPayment(processor1, "1234567890", 100.0);
        processPayment(processor2, "user_abc123", 50.50);
    }

    public static void processPayment(PaymentProcessor processor, String identifier, double amount) {
        System.out.println("Processing payment through: " + processor.getClass().getSimpleName());
        processor.processPayment(identifier, amount);
    }

    public interface PaymentProcessor {
        void processPayment(String identifier, double amount);
    }

    // Simulate Paypal Gateway with a different method name
    public static class Paypal {
        public void makePayment(String account, double amount) {
            System.out.println("Simulating Paypal payment with account: " + account + ", amount: $" + amount);
        }
    }

    // Paypal Adapter implements PaymentProcessor and translates calls
    public static class PaypalAdapter implements PaymentProcessor {
        private final Paypal paypal = new Paypal();

        @Override
        public void processPayment(String identifier, double amount) {
            paypal.makePayment(identifier, amount);
        }
    }

    // Simulate Stripe Gateway with different arguments
    public static class Stripe {
        public void charge(String customerId, double amount) {
            System.out.println("Simulating Stripe charge with customer: " + customerId + ", amount: $" + amount);
        }
    }

    // Stripe Adapter implements PaymentProcessor and translates calls
    public static class StripeAdapter implements PaymentProcessor {
        private final Stripe stripe = new Stripe();

        @Override
        public void processPayment(String identifier, double amount) {
            stripe.charge(identifier, amount);
        }
    }
}
