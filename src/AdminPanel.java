import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class AdminPanel extends JPanel {
    private final JFrame parentFrame;

    public AdminPanel(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout(10, 10));

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            parentFrame.setContentPane(new LoginPanel(parentFrame));
            parentFrame.revalidate();
        });
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        add(menuBar, BorderLayout.NORTH);

        // Header
        JLabel header = new JLabel("Administrator Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.CENTER);

        // Menu
        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        String[] buttons = {
            "Register Student", 
            "Manage Courses", 
            "Generate Reports"
        };

        for (String btnText : buttons) {
            JButton btn = new JButton(btnText);
            btn.addActionListener(this::handleAdminActions);
            menuPanel.add(btn);
        }

        add(menuPanel, BorderLayout.SOUTH);
    }

    private void handleAdminActions(ActionEvent e) {
        String command = ((JButton)e.getSource()).getText();
        switch (command) {
            case "Register Student":
                parentFrame.setContentPane(new StudentRegistrationForm(parentFrame));
                parentFrame.revalidate();
                break;
            case "Manage Courses":
                parentFrame.setContentPane(new CourseManagementPanel(parentFrame));
                parentFrame.revalidate();
                break;
            case "Generate Reports":
                JOptionPane.showMessageDialog(this, "Report generation functionality");
                break;
        }
    }
}