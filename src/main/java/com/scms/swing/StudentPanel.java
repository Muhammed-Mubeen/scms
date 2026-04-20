package com.scms.swing;

import com.scms.model.Department;
import com.scms.model.Student;
import com.scms.service.CourseService;
import com.scms.service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentPanel extends JPanel {

    private final StudentService studentService = new StudentService();
    private final CourseService  courseService  = new CourseService();

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public StudentPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));
        buildUI();
        loadStudents();
    }

    private void buildUI() {
        // ── Top bar ──────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(248, 250, 252));
        topBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("👤 Students");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(26, 58, 92));

        JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBar.setOpaque(false);

        searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        JButton searchBtn = new JButton("🔍 Search");
        JButton addBtn    = new JButton("+ Add Student");
        JButton refreshBtn = new JButton("↻ Refresh");

        addBtn.setBackground(new Color(26, 58, 92));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);

        searchBtn.addActionListener(e -> searchStudents());
        addBtn.addActionListener(e -> showAddDialog());
        refreshBtn.addActionListener(e -> loadStudents());

        rightBar.add(new JLabel("Search: "));
        rightBar.add(searchField);
        rightBar.add(searchBtn);
        rightBar.add(addBtn);
        rightBar.add(refreshBtn);

        topBar.add(title, BorderLayout.WEST);
        topBar.add(rightBar, BorderLayout.EAST);

        // ── Table ────────────────────────────────────────────
        String[] cols = {"ID", "Roll No", "Name", "Email", "Department", "Semester", "Batch"};
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

        // ── Bottom action bar ────────────────────────────────
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomBar.setOpaque(false);
        bottomBar.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton editBtn   = new JButton("✏️ Edit");
        JButton deleteBtn = new JButton("🗑 Delete");

        editBtn.addActionListener(e -> showEditDialog());
        deleteBtn.addActionListener(e -> deleteSelected());

        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);

        bottomBar.add(editBtn);
        bottomBar.add(deleteBtn);

        // ── Assemble ─────────────────────────────────────────
        add(topBar,    BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomBar,  BorderLayout.SOUTH);
    }

    // ── Load all students into table ─────────────────────────
    public void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = studentService.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                    s.getStudentId(),
                    s.getRollNumber(),
                    s.getName(),
                    s.getEmail(),
                    s.getDepartmentName(),
                    s.getSemester(),
                    s.getBatchYear()
            });
        }
    }

    // ── Search ───────────────────────────────────────────────
    private void searchStudents() {
        String keyword = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        List<Student> students = studentService.getAllStudents();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword)
                    || s.getRollNumber().toLowerCase().contains(keyword)) {
                tableModel.addRow(new Object[]{
                        s.getStudentId(), s.getRollNumber(), s.getName(),
                        s.getEmail(), s.getDepartmentName(), s.getSemester(), s.getBatchYear()
                });
            }
        }
    }

    // ── Add dialog ───────────────────────────────────────────
    private void showAddDialog() {
        List<Department> depts = courseService.getAllDepartments();
        JTextField rollField  = new JTextField();
        JTextField nameField  = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JComboBox<Department> deptBox = new JComboBox<>(depts.toArray(new Department[0]));
        JSpinner semSpinner  = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        JSpinner batchSpinner = new JSpinner(new SpinnerNumberModel(2022, 2000, 2099, 1));

        JPanel form = buildFormPanel(new String[]{
                "Roll Number", "Full Name", "Email", "Phone",
                "Password", "Department", "Semester", "Batch Year"
        }, new JComponent[]{
                rollField, nameField, emailField, phoneField,
                passField, deptBox, semSpinner, batchSpinner
        });

        int result = JOptionPane.showConfirmDialog(
                this, form, "Add New Student",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Student s = new Student();
                s.setRollNumber(rollField.getText().trim());
                s.setName(nameField.getText().trim());
                s.setEmail(emailField.getText().trim());
                s.setPhone(phoneField.getText().trim());
                s.setDepartmentId(((Department) deptBox.getSelectedItem()).getDeptId());
                s.setSemester((Integer) semSpinner.getValue());
                s.setBatchYear((Integer) batchSpinner.getValue());
                String password = new String(passField.getPassword());

                studentService.addStudent(s, password);
                loadStudents();
                JOptionPane.showMessageDialog(this, "✅ Student added successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── Edit dialog ──────────────────────────────────────────
    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }

        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        Student s = studentService.getStudentById(studentId);
        List<Department> depts = courseService.getAllDepartments();

        JTextField rollField  = new JTextField(s.getRollNumber());
        JTextField nameField  = new JTextField(s.getName());
        JTextField emailField = new JTextField(s.getEmail());
        JTextField phoneField = new JTextField(s.getPhone());
        JComboBox<Department> deptBox = new JComboBox<>(depts.toArray(new Department[0]));
        JSpinner semSpinner   = new JSpinner(new SpinnerNumberModel(s.getSemester(), 1, 8, 1));
        JSpinner batchSpinner = new JSpinner(new SpinnerNumberModel(s.getBatchYear(), 2000, 2099, 1));

        // Pre-select department
        for (int i = 0; i < depts.size(); i++) {
            if (depts.get(i).getDeptId() == s.getDepartmentId()) {
                deptBox.setSelectedIndex(i);
                break;
            }
        }

        JPanel form = buildFormPanel(new String[]{
                "Roll Number", "Full Name", "Email", "Phone",
                "Department", "Semester", "Batch Year"
        }, new JComponent[]{
                rollField, nameField, emailField, phoneField,
                deptBox, semSpinner, batchSpinner
        });

        int result = JOptionPane.showConfirmDialog(
                this, form, "Edit Student",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                s.setRollNumber(rollField.getText().trim());
                s.setName(nameField.getText().trim());
                s.setEmail(emailField.getText().trim());
                s.setPhone(phoneField.getText().trim());
                s.setDepartmentId(((Department) deptBox.getSelectedItem()).getDeptId());
                s.setSemester((Integer) semSpinner.getValue());
                s.setBatchYear((Integer) batchSpinner.getValue());

                studentService.updateStudent(s);
                loadStudents();
                JOptionPane.showMessageDialog(this, "✅ Student updated successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── Delete ───────────────────────────────────────────────
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to delete this student?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            studentService.deleteStudent(studentId);
            loadStudents();
            JOptionPane.showMessageDialog(this, "✅ Student deleted.");
        }
    }

    // ── Helper — build a labeled form panel ─────────────────
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