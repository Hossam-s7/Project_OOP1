import java.io.*;
import java.util.Scanner;
    public class Student extends User {
    private String studentId;
    private static final String STUDENT_FILE = "students.txt";
    private static final String ENROLLMENT_FILE = "enrollments.txt";
    Enrollment enrollment;
    
    public Student(String userId, String username, String password, String name, 
                  String email, String contactInfo,String studentId, String courseId, String status){    
        super(userId, username, password, name, email, contactInfo);
        // this.studentId = studentId;
        this.enrollment=new Enrollment(studentId, courseId, status)  ;
    }

        

    
     public   boolean  login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && 
                    parts[1].equals(username) && 
                    parts[2].equals(password)) {
                    
                    return true;
                    
                }
            }
        } catch (IOException e) {
            System.out.println("Error accessing student data: " + e.getMessage());
        }
        return false;
    }


    public void registerForCourse(String courseId) {
        String enrollmentData = studentId + "," + courseId + ",Enrolled,N/A";
        FileManager.saveToFile("enrollments.txt", enrollmentData);
        System.out.println("Registered for course " + courseId);
    }
    public void dropCourse(String courseId) {
        try {
            File inputFile = new File("enrollments.txt");
            File tempFile = new File("temp.txt");
    
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    
            String line;
            boolean found = false;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts.length >= 2 && parts[0].equals(this.studentId) && parts[1].equals(courseId)) {
                    found = true;
                    continue; 
                }
                writer.write(line + System.lineSeparator());
            }
    
            writer.close();
            reader.close();
    
            if (found) {
                if (inputFile.delete()) {
                    tempFile.renameTo(inputFile);
                    System.out.println("Successfully dropped course " + courseId);
                } else {
                    System.out.println("Failed to update enrollments file");
                }
            } else {
                tempFile.delete(); 
                System.out.println("No enrollment found for course " + courseId);
            }
    
        } catch (IOException e) {
            System.out.println("Error dropping course: " + e.getMessage());
        }
    }

    

    public void viewGrades() {
        System.out.println("Displaying grades...");
        FileManager.displayFileContents("grades.txt");
    }
    public void calculateGPA() {
        

        
            Scanner scanner = new Scanner(System.in);
            
            
    
            
                
                System.out.println("\n GPA Calculator");
                
                
                
                System.out.print("Enter total program hours: ");
                int totalHours = scanner.nextInt();
                
                
                System.out.print("Enter number of courses: ");
                int numCourses = scanner.nextInt();
                
                double totalPoints = 0;
                int enteredHours = 0;
                
                System.out.println("\nEnter course details:");
                
                for (int i = 1; i <= numCourses; i++) {
                    System.out.println("\nCourse " + i);
                    
                    System.out.print("Course name: ");
                    String name = scanner.next();
                    
                    System.out.print("Credit hours: ");
                    int hours = scanner.nextInt();
                    
                    System.out.print("Your grade (0-100): ");
                    double grade = scanner.nextDouble();
                    double courseGPA = calculateGPA(grade);
                    
                    System.out.println("\nCourse Result:");
                    System.out.println(name + " - " + hours + " hrs");
                    System.out.println("Grade: " + grade + " -> GPA: " + courseGPA);
                    
                    
                    totalPoints += courseGPA * hours;
                    enteredHours += hours;
                }
                
                
                if (enteredHours > 0) {
                    double gpa = totalPoints / enteredHours;
                    System.out.println("\nFinal Result:");
                    
                    System.out.println("Total GPA: " + gpa);
                    System.out.println("Completed: " + enteredHours + "/" + totalHours + " hours");
                }
                
                
            }
            
            private double calculateGPA(double grade) {
                if (grade >= 90) return 4.0;
                if (grade >= 85) return 3.7;
                if (grade >= 80) return 3.3;
                if (grade >= 75) return 3.0;
                if (grade >= 70) return 2.7;
                if (grade >= 65) return 2.3;
                if (grade >= 60) return 2.0;
                if (grade >= 55) return 1.7;
                if (grade >= 50) return 1.3;
                if (grade >= 45) return 1.0;
                return 0.0;
            }
    @Override
    public void showMenu() {
        System.out.println("\nStudent Menu:");
        System.out.println("1. Register for a course");
        System.out.println("2. Drop a course");
        System.out.println("3. View grades");
        System.out.println("4. Calculate GPA");
        System.out.println("5. Update profile");
        System.out.println("6. Change password");
        System.out.println("7. Logout");
    }

    
    public String getStudentId() { 
        return studentId; 
    }
    @Override
          double calculatePayroll() {
         return 0.0;
     }
}
