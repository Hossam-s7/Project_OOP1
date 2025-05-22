import java.io.*;
import java.util.Scanner;
public class UniversitySystem {
    private Department csDepartment;
    private Enrollment currentEnrollment;

    public UniversitySystem() {
        this.csDepartment = new Department("D001", "Computer Science");
    }

    public void startSystem() {
        Scanner scanner = new Scanner(System.in);
        
        
        Student student = new Student("U001", "student1", "pass123", "John Doe", 
                                    "john@uni.edu", "123456789", "S2023001", "CS101", "Enrolled" );
                                    
        Faculty faculty = new Faculty("U002", "prof1", "prof123", "Dr. Smith", 
                                    "smith@uni.edu", "987654321", "F1001", 
                                    csDepartment);
                                    
        AdminStaff admin = new AdminStaff("U003", "admin1", "admin123", "Admin User", 
                                        "admin@uni.edu", "555555555", "A1001", 
                                        csDepartment, "Registrar");
                                        
        SystemAdmin sysAdmin = new SystemAdmin("U004", "sysadmin", "sys123", "Sys Admin", 
                                            "sys@uni.edu", "111111111", "SA001", 
                                            "High");

        System.out.println("Welcome to University Management System");
        
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        User currentUser = null;
        
        if (student.login(username, password)) {
            currentUser = student;
            this.currentEnrollment = new Enrollment(student.getStudentId(), "CS101", "Enrolled");
        } else if (faculty.login(username, password)) {
            currentUser = faculty;
        } else if (admin.login(username, password)) {
            currentUser = admin;
        } else if (sysAdmin.login(username, password)) {
            currentUser = sysAdmin;
        } else {
            System.out.println("Invalid credentials. Exiting");
            System.exit(0);
        }
        
        
        boolean running = true;
        while (running) {
            currentUser.showMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            if (currentUser instanceof Student) {
                handleStudentMenu((Student)currentUser, choice, scanner);
            } 
            else if (currentUser instanceof Faculty) {
                handleFacultyMenu((Faculty)currentUser, choice, scanner);
            }
            else if (currentUser instanceof AdminStaff) {
                handleAdminMenu((AdminStaff)currentUser, choice, scanner);
            }
            else if (currentUser instanceof SystemAdmin) {
                handleSystemAdminMenu((SystemAdmin)currentUser, choice, scanner);
            }
            
            if (choice == 7) {
                currentUser.logout();
                running = false;
            }
        }
        
        scanner.close();
        System.out.println("Goodbye!");
    }

    private void handleStudentMenu(Student student, int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                System.out.print("Enter course ID to register: ");
                String courseId = scanner.nextLine();
                student.registerForCourse(courseId);
                break;
            case 2:
                System.out.print("Enter course ID to drop: ");
                courseId = scanner.nextLine();
                student.dropCourse(courseId);
                break;
            case 3:
                student.viewGrades();
                break;
            case 4:
                student.calculateGPA();
                break;
            case 5:
                updateProfile(student, scanner);
                break;
            case 6:
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                student.changePassword(newPassword,"student");
                break;
        }
    }

    private void handleFacultyMenu(Faculty faculty, int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                System.out.print("Enter student ID: ");
                String studentId = scanner.nextLine();
                System.out.print("Enter course ID: ");
                String courseId = scanner.nextLine();
                System.out.print("Enter grade: ");
                String grade = scanner.nextLine();
                faculty.assignGrades(studentId, courseId, grade);
                break;
            case 2:
                System.out.print("Enter course ID to manage: ");
                courseId = scanner.nextLine();
                faculty.manageCourse(courseId);
                break;
            case 3:
                
                handleOfficeHoursSetting(faculty, scanner);
                break;
            case 4:
                System.out.print("Enter course ID: ");
                courseId = scanner.nextLine();
                faculty.viewStudentRoster(courseId);
                break;
            case 5:
                updateProfile(faculty, scanner);
                break;
            case 6:
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                faculty.changePassword(newPassword,"faculty");
                break;
        }
    }
    private void handleOfficeHoursSetting(Faculty faculty, Scanner scanner) {
        System.out.println("\nOffice Hours Management");
        System.out.println("1. Set new office hours");
        System.out.println("2. View current office hours");
        System.out.println("3. Remove office hours");
        System.out.print("Select an option: ");
        
        int subChoice = scanner.nextInt();
        scanner.nextLine();
        
        switch (subChoice) {
            case 1:
                System.out.print("Enter office hours (Format: 9:00 AM - 5:00 PM): ");
                String hours = scanner.nextLine();
                faculty.setOfficeHours(hours);
                break;
            case 2:
                String currentHours = faculty.getOfficeHours();
                if (currentHours != null && !currentHours.isEmpty()) {
                    System.out.println("Current office hours: " + currentHours);
                } else {
                    System.out.println("No office hours set yet.");
                }
                break;
            case 3:
                faculty.setOfficeHours("");
                System.out.println("Office hours removed successfully.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void handleAdminMenu(AdminStaff admin, int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                Student newStudent = new Student(
                    "U00" + (int)(Math.random()*1000), 
                    "newstudent", "newpass", "New Student",
                    "new@uni.edu", "999999999","S2023","CS101", "Enrolled" + (int)(Math.random()*1000));
                admin.registerStudent(newStudent);
                break;
            case 2:
                System.out.print("Enter course ID: ");
                String courseId = scanner.nextLine();
                System.out.print("Enter course title: ");
                String title = scanner.nextLine();
                System.out.print("Enter course description: ");
                String desc = scanner.nextLine();
                System.out.print("Enter credit hours: ");
                String credits = scanner.nextLine();
                admin.createCourse(courseId, title, desc, credits);
                break;
            case 3:
                System.out.print("Enter faculty name: ");
                String facultyName = scanner.nextLine();
                System.out.print("Enter course ID: ");
                courseId = scanner.nextLine();
                Faculty faculty = new Faculty(
                    "U00" + (int)(Math.random()*1000), 
                    "faculty" + (int)(Math.random()*100), "facpass", 
                    facultyName, "faculty@uni.edu", "888888888", 
                    "F" + (int)(Math.random()*1000), csDepartment);
                admin.assignFaculty(faculty, courseId);
                break;
            case 4:
                admin.generateReports();
                break;
            case 5:
                updateProfile(admin, scanner);
                break;
            case 6:
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                admin.changePassword(newPassword,"admin");
                break;
        }
    }
    private void handleSystemAdminMenu(SystemAdmin sysAdmin, int choice, Scanner scanner) {
    switch (choice) {
        case 1:
            String userType = scanner.nextLine();
            sysAdmin.createUser(userType, 
                String.valueOf((int)(Math.random()*1000)), 
                "newuser", "newpass", "New User", 
                "newuser@uni.edu", "777777777");
            break;
        case 2:
            String userIdToDelete = scanner.nextLine();
            sysAdmin.deleteUser(userIdToDelete);
            break;
        case 3:
            sysAdmin.backupData();
            break;
        case 4:
            updateProfile(sysAdmin, scanner);
            break;
        case 5:
            String newPassword = scanner.nextLine();
            sysAdmin.changePassword(newPassword, "systemadmin");
            break;
    }
}

    
    private void updateProfile(User user, Scanner scanner) {
        try {
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine();
            System.out.print("Enter new contact info: ");
            String newContact = scanner.nextLine();
    
            
            user.updateProfile(newName, newEmail, newContact);
    
            
            String baseData = user.getUserId() + "," + user.getUsername() + "," + 
                             user.getPassword() + "," + newName + "," + 
                             newEmail + "," + newContact;
    
            
            String[] possibleFiles = {
                "users.txt",
                "students.txt",
                "faculty.txt",
                "admin_staff.txt",
                "system_admins.txt"
            };
    
            
            boolean updatedInAnyFile = false;
            
            for (String filename : possibleFiles) {
                File file = new File(filename);
                if (file.exists()) {
                    updateUserInFile(filename, user.getUserId(), baseData);
                    updatedInAnyFile = true;
                }
            }
    
            if (updatedInAnyFile) {
                System.out.println("Profile updated successfully in all system files!");
            } else {
                System.out.println("Profile updated locally but no files were found!");
            }
    
        } catch (Exception e) {
            System.out.println("Error updating profile: " + e.getMessage());
        }
    }


        private boolean updateUserInFile(String filename, String userId, String newData) {
             File inputFile = new File(filename);
             File tempFile = new File(filename + ".tmp");

             try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                   BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                  String line;
                  boolean found = false;
                  while ((line = reader.readLine()) != null) {
                  if (line.startsWith(userId + ",")) {
                     writer.write(newData);
                     found = true;
                    } else {
                   writer.write(line);
                  }
                  writer.newLine();
                  }

                  if (!found) {
                      writer.write(newData);
                      writer.newLine();
                  }

                 } catch (IOException e) {
                 System.out.println("Error updating file: " + e.getMessage());
                 return false;
                }
                if (!inputFile.delete()) {
                    System.out.println("Could not delete original file");
                    return false;
                }

                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Could not rename temp file");
                    return false;
                }

                return true;
            }


}

