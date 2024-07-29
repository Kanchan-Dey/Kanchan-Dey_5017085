import java.util.ArrayList;
import java.util.List;

// Step 2: Define Subject Interface
interface Stock {
    void registerObserver(Observer observer);
    void deregisterObserver(Observer observer);
    void notifyObservers();
}

// Step 3: Implement Concrete Subject
class StockMarket implements Stock {
    private List<Observer> observers;
    private double stockPrice;

    public StockMarket() {
        observers = new ArrayList<>();
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockPrice);
        }
    }
}

// Step 4: Define Observer Interface
interface Observer {
    void update(double stockPrice);
}

// Step 5: Implement Concrete Observers

class MobileApp implements Observer {
    @Override
    public void update(double stockPrice) {
        System.out.println("Mobile App: Stock price updated to " + stockPrice);
    }
}

class WebApp implements Observer {
    @Override
    public void update(double stockPrice) {
        System.out.println("Web App: Stock price updated to " + stockPrice);
    }
}

// Step 6: Test the Observer Implementation
 class ObserverPatternExample {
    public static void main(String[] args) {
        // Create a StockMarket instance
        StockMarket stockMarket = new StockMarket();

        // Create observers
        Observer mobileApp = new MobileApp();
        Observer webApp = new WebApp();

        // Register observers
        stockMarket.registerObserver(mobileApp);
        stockMarket.registerObserver(webApp);

        // Update stock price
        System.out.println("Setting stock price to 100.0");
        stockMarket.setStockPrice(100.0); // This should notify all registered observers

        // Change stock price
        System.out.println("\nSetting stock price to 105.5");
        stockMarket.setStockPrice(105.5); // This should notify all registered observers

        // Deregister an observer
        stockMarket.deregisterObserver(mobileApp);

        // Change stock price again
        System.out.println("\nSetting stock price to 110.0");
        stockMarket.setStockPrice(110.0); // This should notify only the remaining observers
    }
}
