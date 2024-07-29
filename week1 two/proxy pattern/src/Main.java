// Step 2: Define Subject Interface
interface Image {
    void display();
}

// Step 3: Implement Real Subject Class
class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadImageFromServer();
    }

    private void loadImageFromServer() {
        System.out.println("Loading image: " + filename + " from server...");
        // Simulate loading image from a remote server
    }

    @Override
    public void display() {
        System.out.println("Displaying image: " + filename);
    }
}

// Step 4: Implement Proxy Class
class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename); // Lazy initialization
        }
        realImage.display();
    }
}

// Step 5: Test the Proxy Implementation
 class ProxyPatternExample {
    public static void main(String[] args) {
        Image image1 = new ProxyImage("image1.jpg");
        Image image2 = new ProxyImage("image2.jpg");

        System.out.println("Requesting image1...");
        image1.display(); // This will load and display the image

        System.out.println("\nRequesting image1 again...");
        image1.display(); // This will display the cached image

        System.out.println("\nRequesting image2...");
        image2.display(); // This will load and display a new image
    }
}
