import java.util.Random;

class Order {
    int orderId;
    String customerName;
    double totalPrice;

    public Order(int orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Customer Name: " + customerName + ", Total Price: $" + totalPrice;
    }
}

 class OrderSorting {
    public static void main(String[] args) {
        Random random = new Random();
        Order[] orders = new Order[20];

        // Generate 20 random orders
        for (int i = 0; i < orders.length; i++) {
            orders[i] = new Order(i + 1, "Customer " + (i + 1), random.nextDouble() * 1000);
        }

        // Print unsorted orders
        System.out.println("Unsorted orders:");
        printOrders(orders);

        // Bubble sort by total price (ascending)
        long startTime = System.nanoTime();
        bubbleSort(orders);
        long endTime = System.nanoTime();
        long bubbleSortTime = endTime - startTime;

        // Print sorted orders (bubble sort)
        System.out.println("\nSorted orders (Bubble Sort):");
        printOrders(orders);
        System.out.println("Bubble Sort Time Complexity: O(n^2)");
        System.out.println("Bubble Sort Time: " + bubbleSortTime + " nanoseconds\n");

        // Reset orders to original state (important for accurate timing)
        for (int i = 0; i < orders.length; i++) {
            orders[i] = new Order(i + 1, "Customer " + (i + 1), random.nextDouble() * 1000);
        }

        // Quick sort by total price (ascending)
        startTime = System.nanoTime();
        quickSort(orders, 0, orders.length - 1);
        endTime = System.nanoTime();
        long quickSortTime = endTime - startTime;

        // Print sorted orders (quick sort)
        System.out.println("\nSorted orders (Quick Sort):");
        printOrders(orders);
        System.out.println("Quick Sort Time Complexity: O(n log n) on average");
        System.out.println("Quick Sort Time: " + quickSortTime + " nanoseconds");
    }

    public static void printOrders(Order[] orders) {
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    public static void bubbleSort(Order[] orders) {
        for (int i = 0; i < orders.length - 1; i++) {
            for (int j = 0; j < orders.length - i - 1; j++) {
                if (orders[j].totalPrice > orders[j + 1].totalPrice) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high);
            quickSort(orders, low, pivotIndex - 1);
            quickSort(orders, pivotIndex + 1, high);
        }
    }

    public static int partition(Order[] orders, int low, int high) {
        Order pivot = orders[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (orders[j].totalPrice <= pivot.totalPrice) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }

        Order temp = orders[i + 1];
        orders[i + 1] = pivot;
        orders[high] = temp;
        return i + 1;
    }
}
