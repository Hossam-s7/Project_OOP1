import java.io.*;

public class SystemAdmin extends User {
    private String adminId;
    private String securityLevel;

    public SystemAdmin(String userId, String username, String password, String name, 
                      String email, String contactInfo, String adminId, 
                      String securityLevel) {
        super(userId, username, password, name, email, contactInfo);
        this.adminId = adminId;
        this.securityLevel = securityLevel;
    }

    public void createUser(String userType, String userId, String username, String password, 
                         String name, String email, String contactInfo) {
        try {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(userId + "," + username + "," + password + "," +
                         name + "," + email + "," + contactInfo + "," + userType + "\n");
            writer.close();
            System.out.println(userType + " user created successfully.");
        } catch (IOException e) {
            System.out.println("Error while creating user.");
        }
    }

    public void deleteUser(String userIdToDelete) {
        File inputFile = new File("users.txt");
        File tempFile = new File("users_temp.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(tempFile);

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(userIdToDelete + ",")) {
                    writer.write(line + "\n");
                } else {
                    found = true;
                }
            }

            writer.close();
            reader.close();

            if (found) {
                inputFile.delete();
                tempFile.renameTo(inputFile);
                System.out.println("User deleted successfully.");
            } else {
                tempFile.delete();
                System.out.println("User ID not found.");
            }

        } catch (IOException e) {
            System.out.println("Error deleting user.");
        }
    }

    public void viewAllUsers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;
            System.out.println("All users:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading users file.");
        }
    }

    public void changeSecurityLevel(String newLevel) {
        this.securityLevel = newLevel;
        System.out.println("Security level changed to: " + newLevel);
    }

    public void backupData() {
        copyFile("users.txt", "users_backup.txt");
        System.out.println("Backup done.");
    }

    private void copyFile(String sourceFile, String destFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
            FileWriter writer = new FileWriter(destFile);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error backing up file.");
        }
    }

    public void showMenu() {
        System.out.println("\nSystem Admin Menu:");
        System.out.println("1. Create user");
        System.out.println("2. Delete user");
        System.out.println("3. View all users");
        System.out.println("4. Change security level");
        System.out.println("5. Backup data");
        System.out.println("6. Logout");
    }

    @Override
    double calculatePayroll() {
        return 0.0;
    }
}



