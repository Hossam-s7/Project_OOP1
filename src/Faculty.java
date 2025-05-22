import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Faculty extends User {
    protected  String facultyId;
    private Department department;
    private String OfficeHours;
    private static final String FACULTY_FILE = "faculty.txt";
    private static final String ENROLLMENT_FILE = "enrollments.txt";
    public Faculty(String userId, String username, String password, String name, 
                  String email, String contactInfo, String facultyId, 
                  Department department) {
        super(userId, username, password, name, email, contactInfo);
        this.facultyId = facultyId;
        this.department = department;
    }
    public boolean login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FACULTY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[1].equals(username) && parts[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading faculty data: " + e.getMessage());
        }
        return false;
    }


    public void assignGrades(String studentId, String courseId, String grade) {
        FileManager.updateGradeInFile("enrollments.txt", studentId, courseId, grade);
        FileManager.saveGradeToFile(studentId, courseId, grade); 
        System.out.println("Assigned grade " + grade + " to student " + studentId);
    }

    // public void manageCourse(String courseId) {
    //     System.out.println("Managing course " + courseId);
    
    public void manageCourse(String courseId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nManaging course " + courseId);
        System.out.println("1. Add course material");
        System.out.println("2. Update course material");
        System.out.println("3. Remove course material");
        System.out.println("4. View course materials");
        System.out.print("Select an option: ");
        
        try {
            int option = scanner.nextInt();
             scanner.nextLine(); // Consume newline
            
            switch (option) {
                case 1:
                    System.out.print("Enter material title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter material content: ");
                    String content = scanner.nextLine();
                    FileManager.addCourseMaterial(courseId, title, content);
                    System.out.println("Material added successfully.");
                    break;
                case 2:
                    System.out.print("Enter material title to update: ");
                    String oldTitle = scanner.nextLine();
                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter new content: ");
                    String newContent = scanner.nextLine();
                    FileManager.updateCourseMaterial(courseId, oldTitle, newTitle, newContent);
                    System.out.println("Material updated successfully.");
                    break;
                case 3:
                    System.out.print("Enter material title to remove: ");
                    String removeTitle = scanner.nextLine();
                    FileManager.removeCourseMaterial(courseId, removeTitle);
                    System.out.println("Material removed successfully.");
                    break;
                case 4:
                    FileManager.displayCourseMaterials(courseId);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
    }


    
    public void setOfficeHours(String hours) {
        try {
            
            if (hours == null || hours.trim().isEmpty()) {
                throw new IllegalArgumentException("Office hours cannot be empty");
            }
            
            
            if (!hours.matches("^\\d{1,2}:\\d{2}\\s[AP]M\\s-\\s\\d{1,2}:\\d{2}\\s[AP]M$")) {
                throw new IllegalArgumentException("Invalid format. Use 'HH:MM AM/PM - HH:MM AM/PM'");
            }
            
            this.OfficeHours = hours;
            saveOfficeHoursToFile(this.facultyId, hours); // حفظ في ملف
            System.out.println("Office hours set successfully: " + hours);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error saving office hours: " + e.getMessage());
        }
    }

    private void saveOfficeHoursToFile(String facultyId, String hours) throws IOException {
        String fileName = "office_hours.txt";
        String record = facultyId + "," + hours;
        
        
        removeOldOfficeHours(facultyId);
        
        
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(record);
            bw.newLine();
        }
    }

    private void removeOldOfficeHours(String facultyId) throws IOException {
        File inputFile = new File("office_hours.txt");
        File tempFile = new File("temp_office_hours.txt");

        if (!inputFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(facultyId + ",")) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }

        
        Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public String getOfficeHours() {
        return this.OfficeHours;
    }


    public void viewStudentRoster(String courseId) {
        System.out.println("Student roster for course " + courseId + ":");
        FileManager.displayCourseEnrollments("enrollments.txt", courseId);
    }
    @Override
     double calculatePayroll() {
        return 0.0;
     }

    @Override
    public void showMenu() {
        System.out.println("\nFaculty Menu:");
        System.out.println("1. Assign grades");
        System.out.println("2. Manage course");
        System.out.println("3. Set office hours");
        System.out.println("4. View student roster");
        System.out.println("5. Update profile");
        System.out.println("6. Change password");
        System.out.println("7. Logout");
    }
}





























