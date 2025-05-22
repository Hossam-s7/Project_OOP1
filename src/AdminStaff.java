


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AdminStaff extends User {
    private String staffId;
    private Department department;
    private String role;

    private double baseSalary = 5000.0;
    private double bonus = 1200.0;
    private double deductions = 500.0;

    public AdminStaff(String userId, String username, String password, String name, 
                     String email, String contactInfo, String staffId, 
                     Department department, String role) {
        super(userId, username, password, name, email, contactInfo);
        this.staffId = staffId;
        this.department = department;
        this.role = role;
    }

    public void registerStudent(Student student) {
        String userData = student.getUserId() + "," + student.getUsername() + "," + 
                         student.getPassword() + "," + student.getName() + "," + 
                         student.getEmail() + "," + student.getContactInfo();
        FileManager.saveToFile("users.txt", userData);

        String studentData = student.getUserId() + "," + student.getUsername() + "," + 
                           student.getPassword() + "," + student.getName() + "," + 
                           student.getEmail() + "," + student.getContactInfo() + "," + 
                           student.getStudentId() + "," + "2023-09-01" + "," + "Active";
        FileManager.saveToFile("students.txt", studentData);
        
        System.out.println("Student registered successfully.");
    }

    public void createCourse(String courseId, String title, String description, String creditHours) {
        String courseData = courseId + "," + title + "," + description + "," + creditHours;
        FileManager.saveToFile("courses.txt", courseData);
        System.out.println("Course created successfully.");
    }

    public void assignFaculty(Faculty faculty, String courseId) {
        try {
            if (!isCourseExists(courseId)) {
                throw new IllegalArgumentException("Course " + courseId + " does not exist");
            }

            String assignmentData = courseId + "," + faculty.facultyId+ "," + faculty.getName();
            FileManager.saveToFile("course_assignments.txt", assignmentData);

            String facultyAssignment = faculty.facultyId + "," + faculty.getName() + "," + courseId;
            FileManager.saveToFile("faculty_assignments.txt", facultyAssignment);

            sendAssignmentNotification(faculty, courseId);

            System.out.println("Successfully assigned " + faculty.getName() + 
                             " (ID: " + faculty.facultyId + ") to course " + courseId);
            
        } catch (Exception e) {
            System.out.println("Error assigning faculty: " + e.getMessage());
        }
    }

    private boolean isCourseExists(String courseId) {
        try {
            File file = new File("courses.txt");
            if (!file.exists()) return false;

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                return br.lines()
                        .anyMatch(line -> line.startsWith(courseId + ","));
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void sendAssignmentNotification(Faculty faculty, String courseId) {
        String notification = "New Assignment: You have been assigned to teach " + courseId + 
                             "\nFaculty: " + faculty.getName() +
                             "\nDate: " + java.time.LocalDate.now();
        
        try {
            FileManager.saveToFile("notifications_" + faculty.getUserId() + ".txt", notification);
        } catch (Exception e) {
            System.out.println("Could not save notification for faculty " + faculty.facultyId);
        }
    }

    public void generateReports() {
        System.out.println("Generating reports...");
        FileManager.displayFileContents("students.txt");
        FileManager.displayFileContents("courses.txt");
    }

    public double getNetSalary() {
        return calculatePayroll();
    }
    @Override
          double calculatePayroll() {
         return baseSalary + bonus - deductions;
     }

    @Override
    public void showMenu() {
        System.out.println("\nAdmin Staff Menu:");
        System.out.println("1. Register student");
        System.out.println("2. Create course");
        System.out.println("3. Assign faculty");
        System.out.println("4. Generate reports");
        System.out.println("5. Update profile");
        System.out.println("6. Change password");
        System.out.println("7. Logout");
    }
}
