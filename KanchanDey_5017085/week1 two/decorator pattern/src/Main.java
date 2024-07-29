// Step 1: Define the Component Interface
interface Notifier {
    void send(String message);
}

// Step 2: Implement the Concrete Component
class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

// Step 3: Implement the Decorator Classes

// Abstract Decorator Class
abstract class NotifierDecorator implements Notifier {
    protected Notifier decoratedNotifier;

    public NotifierDecorator(Notifier notifier) {
        this.decoratedNotifier = notifier;
    }

    @Override
    public void send(String message) {
        decoratedNotifier.send(message);
    }
}

// Concrete Decorator for SMS
class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message); // Call the original notifier
        sendSMS(message); // Add SMS functionality
    }

    private void sendSMS(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

// Concrete Decorator for Slack
class SlackNotifierDecorator extends NotifierDecorator {
    public SlackNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message); // Call the original notifier
        sendSlack(message); // Add Slack functionality
    }

    private void sendSlack(String message) {
        System.out.println("Sending Slack message: " + message);
    }
}

// Step 4: Test the Decorator Implementation
 class DecoratorPatternExample {
    public static void main(String[] args) {
        // Create an EmailNotifier
        Notifier emailNotifier = new EmailNotifier();

        // Decorate EmailNotifier with SMSNotifierDecorator
        Notifier smsNotifier = new SMSNotifierDecorator(emailNotifier);

        // Decorate EmailNotifier with SlackNotifierDecorator
        Notifier slackNotifier = new SlackNotifierDecorator(emailNotifier);

        // Decorate EmailNotifier with both SMS and Slack decorators
        Notifier combinedNotifier = new SlackNotifierDecorator(smsNotifier);

        // Send notifications through different channels
        System.out.println("Sending notifications through various channels:");
        emailNotifier.send("Hello, World!");
        smsNotifier.send("Hello, World!");
        slackNotifier.send("Hello, World!");
        combinedNotifier.send("Hello, World!");
    }
}
