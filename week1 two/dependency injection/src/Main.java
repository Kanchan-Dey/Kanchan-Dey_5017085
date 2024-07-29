// Step 2: Define Repository Interface
interface CustomerRepository {
    String findCustomerById(int id);
}

// Step 3: Implement Concrete Repository
class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public String findCustomerById(int id) {
        // Simulate database access
        if (id == 1) {
            return "John Doe";
        } else if (id == 2) {
            return "Jane Smith";
        } else {
            return "Customer not found";
        }
    }
}

// Step 4: Define Service Class
class CustomerService {
    private final CustomerRepository customerRepository;

    // Constructor Injection
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String getCustomerName(int id) {
        return customerRepository.findCustomerById(id);
    }
}

// Step 6: Test the Dependency Injection Implementation
class DependencyInjectionExample {
    public static void main(String[] args) {
        // Create an instance of CustomerRepository
        CustomerRepository customerRepository = new CustomerRepositoryImpl();

        // Inject the CustomerRepository into CustomerService
        CustomerService customerService = new CustomerService(customerRepository);

        // Test finding customers
        System.out.println("Customer with ID 1: " + customerService.getCustomerName(1)); // Should print "John Doe"
        System.out.println("Customer with ID 2: " + customerService.getCustomerName(2)); // Should print "Jane Smith"
        System.out.println("Customer with ID 3: " + customerService.getCustomerName(3)); // Should print "Customer not found"
    }
}
