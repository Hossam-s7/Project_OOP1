import java.awt.*;
import javax.swing.*;

public class StudentRegistrationForm extends JPanel {
    private final JFrame parentFrame;

    public StudentRegistrationForm(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel title = new JLabel("Student Registration Form", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, gbc);

        // Form Fields
        String[] labels = {"Full Name:", "Email:", "Phone:", "Student ID:", "Department:"};
        JTextField[] fields = new JTextField[labels.length - 1];
        String[] departments = {"Computer Science", "Engineering", "Business"};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            if (i == labels.length - 1) {
                add(new JComboBox<>(departments), gbc);
            } else {
                fields[i] = new JTextField(20);
                add(fields[i], gbc);
            }
        }

        // Buttons
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton submitBtn = new JButton("Submit");
        JButton cancelBtn = new JButton("Cancel");
        
        submitBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Student registered successfully!");
            parentFrame.setContentPane(new AdminPanel(parentFrame));
            parentFrame.revalidate();
        });
        
        cancelBtn.addActionListener(e -> {
            parentFrame.setContentPane(new AdminPanel(parentFrame));
            parentFrame.revalidate();
        });
        
        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, gbc);
    }
}
