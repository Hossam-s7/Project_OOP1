import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProfessorPanel extends JPanel {
    private final JFrame parentFrame;

    public ProfessorPanel(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel header = new JLabel("Professor Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        // Menu
        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        String[] buttons = {
            "Assign Grades", 
            "Manage Course", 
            "View Student Roster", 
            "Logout"
        };

        for (String btnText : buttons) {
            JButton btn = new JButton(btnText);
            btn.addActionListener(this::handleProfessorActions);
            menuPanel.add(btn);
        }

        add(menuPanel, BorderLayout.CENTER);
    }

    private void handleProfessorActions(ActionEvent e) {
        String command = ((JButton)e.getSource()).getText();
        switch (command) {
            case "Assign Grades":
                JOptionPane.showMessageDialog(this, "Grade assignment functionality");
                break;
            case "Manage Course":
                parentFrame.setContentPane(new CourseManagementPanel(parentFrame));
                parentFrame.revalidate();
                break;
            case "View Student Roster":
                displayRoster();
                break;
            case "Logout":
                parentFrame.setContentPane(new LoginPanel(parentFrame));
                parentFrame.revalidate();
                break;
        }
    }

    private void displayRoster() {
        String[] columns = {"ID", "Name", "Email"};
        Object[][] data = {
            {"S1001", "John Doe", "john@uni.edu"},
            {"S1002", "Jane Smith", "jane@uni.edu"}
        };
        JTable table = new JTable(data, columns);
        JOptionPane.showMessageDialog(this, new JScrollPane(table), 
            "Course Roster", JOptionPane.PLAIN_MESSAGE);
    }
}
