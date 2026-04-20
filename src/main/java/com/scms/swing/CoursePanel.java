package com.scms.swing;

import com.scms.model.Course;
import com.scms.model.Department;
import com.scms.service.CourseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursePanel extends JPanel {

    private final CourseService courseService = new CourseService();

    private JTable table;
    private DefaultTableModel tableModel;

    public CoursePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));
        buildUI();
        loadCourses();
    }

    private void buildUI() {
        // ── Top bar ──────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(248, 250, 252));
        topBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("📚 Courses");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(26, 58, 92));

        JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBar.setOpaque(false);

        JButton addBtn     = new JButton("+ Add Course");
        JButton refreshBtn = new JButton("↻ Refresh");

        addBtn.setBackground(new Color(26, 58, 92));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);

        addBtn.addActionListener(e -> showAddDialog());
        refreshBtn.addActionListener(e -> loadCourses());

        rightBar.add(addBtn);
        rightBar.add(refreshBtn);

        topBar.add(title, BorderLayout.WEST);
        topBar.add(rightBar, BorderLayout.EAST);

        // ── Table ────────────────────────────────────────────
        String[] cols = {"ID", "Code", "Course Name", "Department", "Credits", "Semester"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(26, 58, 92));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));

        // Hide ID column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);

        // ── Bottom bar ───────────────────────────────────────
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomBar.setOpaque(false);
        bottomBar.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton editBtn   = new JButton("✏️ Edit");
        JButton deleteBtn = new JButton("🗑 Delete");

        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);

        editBtn.addActionListener(e -> showEditDialog());
        deleteBtn.addActionListener(e -> deleteSelected());

        bottomBar.add(editBtn);
        bottomBar.add(deleteBtn);

        add(topBar,     BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomBar,  BorderLayout.SOUTH);
    }

    public void loadCourses() {
        tableModel.setRowCount(0);
        List<Course> courses = courseService.getAllCourses();
        for (Course c : courses) {
            tableModel.addRow(new Object[]{
                    c.getCourseId(),
                    c.getCourseCode(),
                    c.getCourseName(),
                    c.getDepartmentName(),
                    c.getCredits(),
                    c.getSemester()
            });
        }
    }

    private void showAddDialog() {
        List<Department> depts = courseService.getAllDepartments();
        JTextField codeField = new JTextField();
        JTextField nameField = new JTextField();
        JComboBox<Department> deptBox = new JComboBox<>(depts.toArray(new Department[0]));
        JSpinner creditsSpinner  = new JSpinner(new SpinnerNumberModel(3, 1, 6, 1));
        JSpinner semesterSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));

        JPanel form = buildFormPanel(
                new String[]{"Course Code", "Course Name", "Department", "Credits", "Semester"},
                new JComponent[]{codeField, nameField, deptBox, creditsSpinner, semesterSpinner}
        );

        int result = JOptionPane.showConfirmDialog(
                this, form, "Add New Course",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Course c = new Course();
                c.setCourseCode(codeField.getText().trim());
                c.setCourseName(nameField.getText().trim());
                c.setDepartmentId(((Department) deptBox.getSelectedItem()).getDeptId());
                c.setCredits((Integer) creditsSpinner.getValue());
                c.setSemester((Integer) semesterSpinner.getValue());
                c.setFacultyId(0);

                courseService.addCourse(c);
                loadCourses();
                JOptionPane.showMessageDialog(this, "✅ Course added successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to edit.");
            return;
        }

        int courseId = (int) tableModel.getValueAt(selectedRow, 0);
        Course c = courseService.getCourseById(courseId);
        List<Department> depts = courseService.getAllDepartments();

        JTextField codeField = new JTextField(c.getCourseCode());
        JTextField nameField = new JTextField(c.getCourseName());
        JComboBox<Department> deptBox = new JComboBox<>(depts.toArray(new Department[0]));
        JSpinner creditsSpinner  = new JSpinner(new SpinnerNumberModel(c.getCredits(), 1, 6, 1));
        JSpinner semesterSpinner = new JSpinner(new SpinnerNumberModel(c.getSemester(), 1, 8, 1));

        for (int i = 0; i < depts.size(); i++) {
            if (depts.get(i).getDeptId() == c.getDepartmentId()) {
                deptBox.setSelectedIndex(i);
                break;
            }
        }

        JPanel form = buildFormPanel(
                new String[]{"Course Code", "Course Name", "Department", "Credits", "Semester"},
                new JComponent[]{codeField, nameField, deptBox, creditsSpinner, semesterSpinner}
        );

        int result = JOptionPane.showConfirmDialog(
                this, form, "Edit Course",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                c.setCourseCode(codeField.getText().trim());
                c.setCourseName(nameField.getText().trim());
                c.setDepartmentId(((Department) deptBox.getSelectedItem()).getDeptId());
                c.setCredits((Integer) creditsSpinner.getValue());
                c.setSemester((Integer) semesterSpinner.getValue());

                courseService.updateCourse(c);
                loadCourses();
                JOptionPane.showMessageDialog(this, "✅ Course updated successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, "Delete this course?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int courseId = (int) tableModel.getValueAt(selectedRow, 0);
            courseService.deleteCourse(courseId);
            loadCourses();
            JOptionPane.showMessageDialog(this, "✅ Course deleted.");
        }
    }

    private JPanel buildFormPanel(String[] labels, JComponent[] fields) {
        JPanel panel = new JPanel(new GridLayout(labels.length, 2, 8, 8));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(420, labels.length * 40));
        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i] + ":"));
            panel.add(fields[i]);
        }
        return panel;
    }
}