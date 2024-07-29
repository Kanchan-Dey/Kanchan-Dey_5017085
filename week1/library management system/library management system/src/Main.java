import java.util.Scanner;

class Book {
    int bookId;
    String title;
    String author;

    Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }
}

 class LibraryManagement {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Book[] books = { // Sample book data (assuming sorted by title)
                new Book(1, "The Lord of the Rings", "J.R.R. Tolkien"),
                new Book(2, "Pride and Prejudice", "Jane Austen"),
                new Book(3, "To Kill a Mockingbird", "Harper Lee"),
                new Book(4, "The Great Gatsby", "F. Scott Fitzgerald"),
                new Book(5, "One Hundred Years of Solitude", "Gabriel Garcia Marquez")
        };

        int choice;

        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Search Book by Title (Linear Search)");
            System.out.println("2. Search Book by Title (Binary Search - Assuming Sorted List)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter book title to search: ");
                    String title = scanner.nextLine();
                    scanner.nextLine(); // Consume newline
                    int index = linearSearch(books, title);
                    if (index != -1) {
                        System.out.println("Book found!");
                        System.out.println("Book ID: " + books[index].bookId);
                        System.out.println("Title: " + books[index].title);
                        System.out.println("Author: " + books[index].author);
                    } else {
                        System.out.println("Book not found");
                    }
                    break;
                case 2:
                    System.out.print("Enter book title to search: ");
                    title = scanner.nextLine();
                    scanner.nextLine();
                    index = binarySearch(books, title);
                    if (index != -1) {
                        System.out.println("Book found!");
                        System.out.println("Book ID: " + books[index].bookId);
                        System.out.println("Title: " + books[index].title);
                        System.out.println("Author: " + books[index].author);
                    } else {
                        System.out.println("Book not found");
                    }
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 3);
    }

    // Linear search for book by title (worst case: O(n))
    public static int linearSearch(Book[] books, String title) {
        for (int i = 0; i < books.length; i++) {
            if (books[i].title.equals(title)) {
                return i;
            }
        }
        return -1; // Book not found
    }

    // Binary search for book by title (assuming sorted list, average case: O(log n))
    public static int binarySearch(Book[] books, String title) {
        int low = 0;
        int high = books.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int compareResult = title.compareTo(books[mid].title);

            if (compareResult == 0) {
                return mid; // Book found at middle index
            } else if (compareResult < 0) {
                high = mid - 1; // Search left half
            } else {
                low = mid + 1; // Search right half
            }
        }
        return -1; // Book not found
    }
}