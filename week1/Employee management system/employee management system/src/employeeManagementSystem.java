import java.util.Scanner;

class Employee {
    private int employeeId;
    private String name;
    private String position;
    private double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // Getter methods for attributes (you can add setters if needed)

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }
}

 class EmployeeManagementSystem {
    private static final int MAX_EMPLOYEES = 100; // Adjust as needed
    private Employee[] employees;
    private int numEmployees;

    public EmployeeManagementSystem() {
        employees = new Employee[MAX_EMPLOYEES];
        numEmployees = 0;
    }

    public void addEmployee(Employee employee) {
        if (numEmployees < MAX_EMPLOYEES) {
            employees[numEmployees++] = employee;
            System.out.println("Employee added successfully.");
        } else {
            System.out.println("Employee database is full. Cannot add more employees.");
        }
    }

    public Employee searchEmployee(int employeeId) {
        for (int i = 0; i < numEmployees; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                return employees[i];
            }
        }
        return null; // Employee not found
    }

    public void traverseEmployees() {
        for (int i = 0; i < numEmployees; i++) {
            System.out.println(employees[i].getName() + " (" + employees[i].getPosition() + ")");
        }
    }

    public void deleteEmployee(int employeeId) {
        for (int i = 0; i < numEmployees; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                // Shift remaining employees to fill the gap
                for (int j = i; j < numEmployees - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                numEmployees--;
                System.out.println("Employee with ID " + employeeId + " deleted successfully.");
                return;
            }
        }
        System.out.println("Employee with ID " + employeeId + " not found.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManagementSystem system = new EmployeeManagementSystem();

        while (true) {
            System.out.println("\nEmployee Management System Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Search Employee");
            System.out.println("3. Traverse Employees");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter employee ID: ");
                    int empId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter employee name: ");
                    String empName = scanner.nextLine();
                    System.out.print("Enter employee position: ");
                    String empPosition = scanner.nextLine();
                    System.out.print("Enter employee salary: ");
                    double empSalary = scanner.nextDouble();
                    Employee newEmployee = new Employee(empId, empName, empPosition, empSalary);
                    system.addEmployee(newEmployee);
                    break;
                case 2:
                    System.out.print("Enter employee ID to search: ");
                    int searchId = scanner.nextInt();
                    Employee foundEmployee = system.searchEmployee(searchId);
                    if (foundEmployee != null) {
                        System.out.println("Employee found: " + foundEmployee.getName());
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;
                case 3:
                    System.out.println("Employee list:");
                    system.traverseEmployees();
                    break;
                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    int deleteId = scanner.nextInt();
                    system.deleteEmployee(deleteId);
                    break;
                case 5:
                    System.out.println("Exiting Employee Management System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
