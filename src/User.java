import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public abstract class User {
    private  String userId;
    private  String username;
    private  String password;
    private  String name;
    private  String email;
    private  String contactInfo;

    public User(String userId, String username, String password, String name, 
                String email, String contactInfo) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
    }

    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    public void logout() {
        System.out.println("Logging out");
    }

    public void updateProfile(String newName, String newEmail, String newContactInfo) {
        this.name = newName;
        this.email = newEmail;
        this.contactInfo = newContactInfo;
        System.out.println("Profile updated successfully.");
    }



    public void changePassword(String newPassword, String userType) {
        if (newPassword.length() < 6) {
            System.out.println("Password must be at least 6 characters long.");
            return;
        }
    
        
        String oldPassword = this.password; 
        this.password = newPassword;
        
        
        String filename = getFilenameByUserType(userType);
        if (filename == null) {
            System.out.println("Error: Unknown user type");
            this.password = oldPassword;
            return;
        }
    
        
        if (updatePasswordInFile(filename, this.userId, oldPassword, newPassword)) {
            System.out.println("Password changed successfully in file");
        } else {
            System.out.println("Password changed in memory but failed to update file");
            this.password = oldPassword; 
        }
    }
    
    private boolean updatePasswordInFile(String filename, String userId, String oldPassword, String newPassword) {
        File inputFile = new File(filename).getAbsoluteFile();
        File tempFile = new File(filename + ".tmp").getAbsoluteFile();
        
        boolean updated = false;
        boolean success = false;
    
        try {
            
            if (!inputFile.getParentFile().exists()) {
                inputFile.getParentFile().mkdirs();
            }
    
            
            File backupFile = new File(filename + ".bak");
            if (inputFile.exists()) {
                Files.copy(inputFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
    
            
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", -1); 
                    
                    
                    if (parts.length > 2 && parts[0].equals(userId)) {
                        if (parts[2].equals(oldPassword)) {
                            parts[2] = newPassword; 
                            writer.write(String.join(",", parts));
                            updated = true;
                        } else {
                            
                            System.err.println("Current password does not match!");
                            writer.write(line); 
                        }
                    } else {
                        writer.write(line); 
                    }
                    writer.newLine();
                }
                 }
    
           
            if (updated) {
                if (inputFile.exists() && !inputFile.delete()) {
                    throw new IOException("Failed to delete original file");
                }
    
                if (!tempFile.renameTo(inputFile)) {
                    throw new IOException("Failed to rename temp file");
                }
    
                
                if (backupFile.exists() && !backupFile.delete()) {
                    System.err.println("Warning: Could not delete backup file");
                }
    
                success = true;
            } else {
                System.err.println("Failed to find user record with matching old password");
            }
    
        } catch (IOException e) {
            System.out.println("Error updating password: " + e.getMessage());
        
            
        } finally {
            
            if (tempFile.exists() && !tempFile.delete()) {
                System.out.println("Warning: Could not delete temp file");
            }
        }
    
        return success;
    }






private String getFilenameByUserType(String userType) {
    switch (userType.toLowerCase()) {
        case "student":
            return "students.txt";
        case "faculty":
            return "faculty.txt";
        case "admin":
            return "admin_staff.txt";
        case "systemadmin":
            return "system_admins.txt";
        default:
            return null;
    }
}



    public abstract void showMenu();

    
    public String getUserId() {
        return userId; 
    }
    public String getUsername() { 
        return username; 
    }
    public String getName() { 
        return name; 
    }
    public String getEmail() { 
        return email; 
    }
    public String getContactInfo() { 
        return contactInfo; 
    }
    public String getPassword() {
        return password;
    } 
    abstract double calculatePayroll();
}