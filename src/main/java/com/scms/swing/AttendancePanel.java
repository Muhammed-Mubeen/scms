package com.scms.swing;

import com.scms.model.Attendance;
import com.scms.model.Course;
import com.scms.model.Student;
import com.scms.service.AttendanceService;
import com.scms.service.CourseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendancePanel extends JPanel {

    private final AttendanceService attendanceService = new AttendanceService();
    private final CourseService     courseService     = new CourseService();

    private JComboBox<Course> courseBox;
    private JSpinner          dateSpinner;
    private JTable            table;
    private DefaultTableModel tableModel;

    public AttendancePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));
        buildUI();
    }

    private void buildUI() {
        // ── Top bar ──────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(248, 250, 252));
        topBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("📋 Attendance");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(26, 58, 92));

        // ── Controls panel ───────────────────────────────────
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.setOpaque(false);
        controls.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Course dropdown
        List<Course> courses = courseService.getAllCourses();
        courseBox = new JComboBox<>(courses.toArray(new Course[0]));
        courseBox.setFont(new Font("Arial", Font.PLAIN, 13));
        courseBox.setPreferredSize(new Dimension(220, 32));

        // Date spinner
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setPreferredSize(new Dimension(130, 32));

        JButton loadBtn = new JButton("Load Students");
        loadBtn.setBackground(new Color(26, 58, 92));
        loadBtn.setForeground(Color.WHITE);
        loadBtn.setFocusPainted(false);
        loadBtn.addActionListener(e -> loadStudentsForAttendance());

        JButton markAllPresentBtn = new JButton("✅ All Present");
        JButton markAllAbsentBtn  = new JButton("❌ All Absent");
        markAllPresentBtn.addActionListener(e -> markAll("P"));
        markAllAbsentBtn.addActionListener(e -> markAll("A"));

        controls.add(new JLabel("Course: "));
        controls.add(courseBox);
        controls.add(Box.createHorizontalStrut(10));
        controls.add(new JLabel("Date: "));
        controls.add(dateSpinner);
        controls.add(Box.createHorizontalStrut(10));
        controls.add(loadBtn);
        controls.add(markAllPresentBtn);
        controls.add(markAllAbsentBtn);

        topBar.add(title,    BorderLayout.NORTH);
        topBar.add(controls, BorderLayout.CENTER);

        // ── Table ────────────────────────────────────────────
        String[] cols = {"student_id", "Roll No", "Name", "Present", "Absent", "Leave"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return c >= 3; // only status columns editable
            }
            @Override public Class<?> getColumnClass(int c) {
                return c >= 3 ? Boolean.class : String.class;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(26, 58, 92));
        table.getTableHeader().setForeground(Color.WHITE);

        // Hide student_id column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        // Make checkbox columns mutually exclusive
        tableModel.addTableModelListener(e -> {
            if (e.getColumn() >= 3) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                if (Boolean.TRUE.equals(tableModel.getValueAt(row, col))) {
                    for (int c = 3; c <= 5; c++) {
                        if (c != col) tableModel.setValueAt(false, row, c);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // ── Bottom bar ───────────────────────────────────────
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomBar.setOpaque(false);
        bottomBar.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton saveBtn = new JButton("💾 Save Attendance");
        saveBtn.setBackground(new Color(26, 58, 92));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> saveAttendance());

        bottomBar.add(saveBtn);

        // ── Assemble ─────────────────────────────────────────
        add(topBar,     BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomBar,  BorderLayout.SOUTH);
    }

    private void loadStudentsForAttendance() {
        tableModel.setRowCount(0);
        Course selectedCourse = (Course) courseBox.getSelectedItem();
        if (selectedCourse == null) return;

        List<Student> students = attendanceService
                .getStudentsForCourse(selectedCourse.getCourseId());

        // Get existing attendance for pre-fill
        java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();
        LocalDate date = selectedDate.toInstant()
                .atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        List<Attendance> existing = attendanceService
                .getAttendanceByCoursAndDate(selectedCourse.getCourseId(), date);

        Map<Integer, String> existingMap = new HashMap<>();
        for (Attendance a : existing) {
            existingMap.put(a.getStudentId(), a.getStatus());
        }

        for (Student s : students) {
            String status = existingMap.getOrDefault(s.getStudentId(), "P");
            tableModel.addRow(new Object[]{
                    s.getStudentId(),
                    s.getRollNumber(),
                    s.getName(),
                    "P".equals(status),   // Present checkbox
                    "A".equals(status),   // Absent checkbox
                    "L".equals(status)    // Leave checkbox
            });
        }
    }

    private void markAll(String status) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt("P".equals(status), i, 3);
            tableModel.setValueAt("A".equals(status), i, 4);
            tableModel.setValueAt("L".equals(status), i, 5);
        }
    }

    private void saveAttendance() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Load students first.");
            return;
        }

        Course selectedCourse = (Course) courseBox.getSelectedItem();
        java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();
        LocalDate date = selectedDate.toInstant()
                .atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        Map<Integer, String> statusMap = new HashMap<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int    studentId = (int) tableModel.getValueAt(i, 0);
            boolean present  = (Boolean) tableModel.getValueAt(i, 3);
            boolean absent   = (Boolean) tableModel.getValueAt(i, 4);
            boolean leave    = (Boolean) tableModel.getValueAt(i, 5);

            String status = present ? "P" : absent ? "A" : leave ? "L" : "A";
            statusMap.put(studentId, status);
        }

        try {
            attendanceService.markAttendance(
                    selectedCourse.getCourseId(), date, statusMap, 1);
            JOptionPane.showMessageDialog(this, "✅ Attendance saved successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}