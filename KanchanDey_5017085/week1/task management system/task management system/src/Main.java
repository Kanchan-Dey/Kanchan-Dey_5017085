
import java.util.Scanner;

class Task {
    int taskId;
    String taskName;
    String status;

    Task(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }
}

class Node {
    Task data;
    Node next;

    Node(Task data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;

    LinkedList() {
        head = null;
    }

    void addTask(Task task) { // Time complexity: O(1) for adding at the beginning
        Node newNode = new Node(task);
        newNode.next = head;
        head = newNode;
    }

    void deleteTask(int taskId) { // Time complexity: O(n) in worst case
        Node temp = head, prev = null;
        while (temp != null && temp.data.taskId != taskId) {
            prev = temp;
            temp = temp.next;
        }
        if (temp == null) {
            System.out.println("Task not found");
            return;
        }
        if (prev == null) {
            head = temp.next;
        } else {
            prev.next = temp.next;
        }
        System.out.println("Task deleted successfully");
    }

    void traverse() { // Time complexity: O(n)
        Node temp = head;
        while (temp != null) {
            System.out.println("Task ID: " + temp.data.taskId + ", Task Name: " + temp.data.taskName + ", Status: " + temp.data.status);
            temp = temp.next;
        }
    }

    void searchTask(int taskId) { // Time complexity: O(n)
        Node temp = head;
        while (temp != null) {
            if (temp.data.taskId == taskId) {
                System.out.println("Task found: Task ID: " + temp.data.taskId + ", Task Name: " + temp.data.taskName + ", Status: " + temp.data.status);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Task not found");
    }
}

 class TaskManagement {
    public static void main(String[] args) {
        LinkedList taskList = new LinkedList();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nTask Management System");
            System.out.println("1. Add Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Traverse Tasks");
            System.out.println("4. Search Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter task ID: ");
                    int taskId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter task name: ");
                    String taskName = scanner.nextLine();
                    System.out.print("Enter task status: ");
                    String status = scanner.nextLine();
                    Task task = new Task(taskId, taskName, status);
                    taskList.addTask(task);
                    break;
                case 2:
                    System.out.print("Enter task ID to delete: ");
                    taskId = scanner.nextInt();
                    taskList.deleteTask(taskId);
                    break;
                case 3:
                    taskList.traverse();
                    break;
                case 4:
                    System.out.print("Enter task ID to search: ");
                    taskId = scanner.nextInt();
                    taskList.searchTask(taskId);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 5);
    }
}
