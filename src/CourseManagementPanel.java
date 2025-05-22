import java.awt.*;
import javax.swing.*;

public class CourseManagementPanel extends JPanel {
    private final JFrame parentFrame;

    public CourseManagementPanel(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Course Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Course Table
        String[] columns = {"Course Code", "Title", "Credit Hours", "Instructor"};
        Object[][] data = {
            {"CS101", "Introduction to Programming", 3, "Dr. Smith"},
            {"MATH201", "Calculus I", 4, "Dr. Johnson"}
        };
        JTable courseTable = new JTable(data, columns);
        add(new JScrollPane(courseTable), BorderLayout.CENTER);

        // Control Panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addBtn = new JButton("Add Course");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");
        
        addBtn.addActionListener(e -> showAddCourseDialog());
        backBtn.addActionListener(e -> {
            if (parentFrame.getContentPane() instanceof ProfessorPanel) {
                parentFrame.setContentPane(new ProfessorPanel(parentFrame));
            } else {
                parentFrame.setContentPane(new AdminPanel(parentFrame));
            }
            parentFrame.revalidate();
        });
        
        controlPanel.add(addBtn);
        controlPanel.add(editBtn);
        controlPanel.add(deleteBtn);
        controlPanel.add(backBtn);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void showAddCourseDialog() {
        JDialog dialog = new JDialog(parentFrame, "Add New Course", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        
        dialog.add(new JLabel("Course Code:"));
        JTextField codeField = new JTextField();
        dialog.add(codeField);
        
        dialog.add(new JLabel("Course Title:"));
        JTextField titleField = new JTextField();
        dialog.add(titleField);
        
        dialog.add(new JLabel("Credit Hours:"));
        JSpinner creditSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 6, 1));
        dialog.add(creditSpinner);
        
        dialog.add(new JLabel("Instructor:"));
        JTextField instructorField = new JTextField();
        dialog.add(instructorField);
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "Course added successfully!");
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        dialog.add(saveBtn);
        dialog.add(cancelBtn);
        dialog.setVisible(true);
    }
}
