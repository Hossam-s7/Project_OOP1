import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class StudentPanel extends JPanel {
    private final JFrame parentFrame;

    public StudentPanel(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel header = new JLabel("Student Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        // Menu
        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        String[] buttons = {
            "Register for Course", 
            "Drop Course", 
            "View Grades", 
            "Logout"
        };

        for (String btnText : buttons) {
            JButton btn = new JButton(btnText);
            btn.addActionListener(this::handleStudentActions);
            menuPanel.add(btn);
        }

        add(menuPanel, BorderLayout.CENTER);
    }

    private void handleStudentActions(ActionEvent e) {
        String command = ((JButton)e.getSource()).getText();
        switch (command) {
            case "Register for Course":
                JOptionPane.showMessageDialog(this, "Course registration functionality");
                break;
            case "Drop Course":
                JOptionPane.showMessageDialog(this, "Course drop functionality");
                break;
            case "View Grades":
                displayGrades();
                break;
            case "Logout":
                parentFrame.setContentPane(new LoginPanel(parentFrame));
                parentFrame.revalidate();
                break;
        }
    }

    private void displayGrades() {
        String[] columns = {"Course", "Grade"};
        Object[][] data = {
            {"CS101", "A"},
            {"MATH201", "B+"},
            {"CS101", "A"},
            {"PHY201", "c+"},
            {"ENG101", "c+"}
        };
        JTable table = new JTable(data, columns);
        JOptionPane.showMessageDialog(this, new JScrollPane(table), 
            "Your Grades", JOptionPane.PLAIN_MESSAGE);
    }
}
