// Step 2: Define Model Class
class Student {
    private String name;
    private int id;
    private String grade;

    // Constructor
    public Student(String name, int id, String grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

// Step 3: Define View Class
class StudentView {
    public void displayStudentDetails(String studentName, int studentId, String studentGrade) {
        System.out.println("Student Details:");
        System.out.println("Name: " + studentName);
        System.out.println("ID: " + studentId);
        System.out.println("Grade: " + studentGrade);
    }
}

// Step 4: Define Controller Class
class StudentController {
    private Student model;
    private StudentView view;

    // Constructor
    public StudentController(Student model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    // Method to update the student details
    public void setStudentName(String name) {
        model.setName(name);
    }

    public String getStudentName() {
        return model.getName();
    }

    public void setStudentId(int id) {
        model.setId(id);
    }

    public int getStudentId() {
        return model.getId();
    }

    public void setStudentGrade(String grade) {
        model.setGrade(grade);
    }

    public String getStudentGrade() {
        return model.getGrade();
    }

    // Method to update the view with student details
    public void updateView() {
        view.displayStudentDetails(model.getName(), model.getId(), model.getGrade());
    }
}

// Step 5: Test the MVC Implementation
class MVCPatternExample {
    public static void main(String[] args) {
        // Create a student model
        Student student = new Student("John Doe", 123, "A");

        // Create a view
        StudentView view = new StudentView();

        // Create a controller
        StudentController controller = new StudentController(student, view);

        // Display initial student details
        System.out.println("Initial student details:");
        controller.updateView();

        // Update student details
        controller.setStudentName("Jane Doe");
        controller.setStudentId(456);
        controller.setStudentGrade("B");

        // Display updated student details
        System.out.println("\nUpdated student details:");
        controller.updateView();
    }
}
