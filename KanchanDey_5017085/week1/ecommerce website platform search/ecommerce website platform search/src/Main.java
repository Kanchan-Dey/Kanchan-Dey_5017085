import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Product {
    int productId;
    String productName;
    String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }
}

 class ECommerceSearch {
    public static void main(String[] args) {
        Product[] products = {
                new Product(1, "Laptop", "Electronics"),
                new Product(2, "Mobile", "Electronics"),
                new Product(3, "TV", "Electronics"),
                new Product(4, "Microwave", "Appliances"),
                new Product(5, "Soundbar", "Electronics")
        };

        // For binary search, create a sorted copy of products
        Product[] sortedProducts = products.clone();
        Arrays.sort(sortedProducts, (p1, p2) -> p1.productName.compareToIgnoreCase(p2.productName));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product to search: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        // Linear search
        long startTime = System.nanoTime();
        List<Product> linearResults = linearSearch(products, searchTerm);
        long endTime = System.nanoTime();
        long linearTime = endTime - startTime;

        // Binary search
        startTime = System.nanoTime();
        List<Product> binaryResults = binarySearch(sortedProducts, searchTerm);
        endTime = System.nanoTime();
        long binaryTime = endTime - startTime;

        System.out.println("Linear Search Results:");
        printResults(linearResults);
        System.out.println("Time taken: " + linearTime + " nanoseconds");

        System.out.println("\nBinary Search Results:");
        printResults(binaryResults);
        System.out.println("Time taken: " + binaryTime + " nanoseconds");

        // Time complexity comparison
        System.out.println("\nTime complexity comparison:");
        System.out.println("Linear search: O(n)");
        System.out.println("Binary search: O(log n)");
    }

    public static List<Product> linearSearch(Product[] products, String searchTerm) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.productName.toLowerCase().contains(searchTerm)) {
                results.add(product);
            }
        }
        return results;
    }

    public static List<Product> binarySearch(Product[] products, String searchTerm) {
        List<Product> results = new ArrayList<>();
        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = products[mid].productName.compareToIgnoreCase(searchTerm);

            if (comparison >= 0) {
                if (products[mid].productName.toLowerCase().contains(searchTerm)) {
                    results.add(products[mid]);
                }
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return results;
    }

    public static void printResults(List<Product> results) {
        if (results.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : results) {
                System.out.println(product.productName);
            }
        }
    }
}
