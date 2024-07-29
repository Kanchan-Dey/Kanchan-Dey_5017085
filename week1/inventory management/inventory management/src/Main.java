import java.util.HashMap;
import java.util.Scanner;

class Product {
    int productId;
    String productName;
    int quantity;
    double price;

    // Constructor
    Product(int id, String name, int qty, double p) {
        productId = id;
        productName = name;
        quantity = qty;
        price = p;
    }
}

class Inventory {
    HashMap<Integer, Product> products;

    Inventory() {
        products = new HashMap<>();
    }

    void addProduct(Product product) {
        products.put(product.productId, product);
    }

    void updateProduct(int productId, int newQuantity, double newPrice) {
        Product product = products.get(productId);
        if (product != null) {
            product.quantity = newQuantity;
            product.price = newPrice;
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    void deleteProduct(int productId) {
        if (products.remove(productId) != null) {
            System.out.println("Product with ID " + productId + " deleted.");
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    void displayInventory() {
        System.out.println("Inventory:");
        for (Product product : products.values()) {
            System.out.println("Product ID: " + product.productId + ", Name: " + product.productName +
                    ", Quantity: " + product.quantity + ", Price: $" + product.price);
        }
    }
}

class InventoryManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();

        while (true) {
            System.out.println("\nInventory Management System");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Display Inventory");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter product ID: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter product name: ");
                    String productName = scanner.next();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    Product product = new Product(productId, productName, quantity, price);
                    inventory.addProduct(product);
                    break;
                case 2:
                    System.out.print("Enter product ID to update: ");
                    productId = scanner.nextInt();
                    System.out.print("Enter new quantity: ");
                    quantity = scanner.nextInt();
                    System.out.print("Enter new price: ");
                    price = scanner.nextDouble();
                    inventory.updateProduct(productId, quantity, price);
                    break;
                case 3:
                    System.out.print("Enter product ID to delete: ");
                    productId = scanner.nextInt();
                    inventory.deleteProduct(productId);
                    break;
                case 4:
                    inventory.displayInventory();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
