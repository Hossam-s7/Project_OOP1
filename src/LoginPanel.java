import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class LoginPanel extends JPanel {
    private final JFrame parentFrame;

    public LoginPanel(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel title = new JLabel("University Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // Buttons
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");
        
        loginBtn.addActionListener(e -> handleLogin(
            usernameField.getText(), 
            new String(passwordField.getPassword())
        ));
        
        exitBtn.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(loginBtn);
        buttonPanel.add(exitBtn);
        add(buttonPanel, gbc);
    }

    private void handleLogin(String username, String password) {
        try {
            // Check in students.txt first
            if (checkCredentials("students.txt", username, password)) {
                parentFrame.setContentPane(new StudentPanel(parentFrame));
                parentFrame.revalidate();
                return;
            }

            // Check in faculty.txt next
            if (checkCredentials("faculty.txt", username, password)) {
                parentFrame.setContentPane(new ProfessorPanel(parentFrame));
                parentFrame.revalidate();
                return;
            }

            // Check admin credentials (you can move this to a file too if needed)
            if (username.equals("admin") && password.equals("123")) {
                parentFrame.setContentPane(new AdminPanel(parentFrame));
                parentFrame.revalidate();
                return;
            }

            // If none matched
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password!", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "Error accessing user data files!",
                "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkCredentials(String filename, String username, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Assuming format: userId,username,password,name,email,contact,...
                if (parts.length >= 3 && parts[1].equals(username) && parts[2].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}