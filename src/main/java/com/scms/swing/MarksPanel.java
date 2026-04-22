package com.scms.swing;

import com.scms.model.Exam;
import com.scms.model.Mark;
import com.scms.model.Student;
import com.scms.service.ResultService;
import com.scms.service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarksPanel extends JPanel {

    private final ResultService  resultService  = new ResultService();
    private final StudentService studentService = new StudentService();

    private JTabbedPane tabs;

    public MarksPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));
        buildUI();
    }

    private void buildUI() {
        JLabel title = new JLabel("📊 Marks & Results");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(26, 58, 92));
        title.setBorder(new EmptyBorder(10, 10, 5, 10));

        tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 13));
        tabs.addTab("Enter Marks", buildEnterMarksTab());
        tabs.addTab("View Results", buildViewResultsTab());

        add(title, BorderLayout.NORTH);
        add(tabs,  BorderLayout.CENTER);
    }

    // ── Tab 1: Enter Marks ───────────────────────────────────
    private JPanel buildEnterMarksTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 250, 252));

        // Controls
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.setBorder(new EmptyBorder(10, 10, 5, 10));
        controls.setOpaque(false);

        List<Exam> exams = resultService.getAllExams();
        JComboBox<Exam> examBox = new JComboBox<>(exams.toArray(new Exam[0]));
        examBox.setPreferredSize(new Dimension(280, 32));
        examBox.setFont(new Font("Arial", Font.PLAIN, 13));

        JButton loadBtn = new JButton("Load Students");
        loadBtn.setBackground(new Color(26, 58, 92));
        loadBtn.setForeground(Color.WHITE);
        loadBtn.setFocusPainted(false);

        controls.add(new JLabel("Select Exam: "));
        controls.add(examBox);
        controls.add(loadBtn);

        // Table
        String[] cols = {"student_id", "Roll No", "Name", "Marks"};
        DefaultTableModel marksModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 3; }
        };
        JTable marksTable = new JTable(marksModel);
        marksTable.setFont(new Font("Arial", Font.PLAIN, 13));
        marksTable.setRowHeight(28);
        marksTable.getTableHeader().setBackground(new Color(26, 58, 92));
        marksTable.getTableHeader().setForeground(Color.WHITE);
        marksTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        // Hide ID column
        marksTable.getColumnModel().getColumn(0).setMinWidth(0);
        marksTable.getColumnModel().getColumn(0).setMaxWidth(0);

        // Load button action
        loadBtn.addActionListener(e -> {
            Exam selectedExam = (Exam) examBox.getSelectedItem();
            if (selectedExam == null) return;
            marksModel.setRowCount(0);

            List<Student> students = resultService.getAllStudents();
            List<Mark> existing    = resultService.getMarksByExam(selectedExam.getExamId());

            Map<Integer, Double> existingMap = new HashMap<>();
            for (Mark m : existing) existingMap.put(m.getStudentId(), m.getMarksObtained());

            for (Student s : students) {
                marksModel.addRow(new Object[]{
                        s.getStudentId(),
                        s.getRollNumber(),
                        s.getName(),
                        existingMap.getOrDefault(s.getStudentId(), 0.0)
                });
            }
        });

        // Save button
        JButton saveBtn = new JButton("💾 Save Marks");
        saveBtn.setBackground(new Color(26, 58, 92));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> {
            Exam selectedExam = (Exam) examBox.getSelectedItem();
            if (selectedExam == null || marksModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Load exam students first.");
                return;
            }
            Map<Integer, Double> marksMap = new HashMap<>();
            for (int i = 0; i < marksModel.getRowCount(); i++) {
                int    id    = (int) marksModel.getValueAt(i, 0);
                double marks = 0.0;
                try { marks = Double.parseDouble(marksModel.getValueAt(i, 3).toString()); }
                catch (NumberFormatException ignored) {}
                marksMap.put(id, marks);
            }
            try {
                resultService.saveMarks(selectedExam.getExamId(), marksMap);
                JOptionPane.showMessageDialog(this, "✅ Marks saved successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomBar.setOpaque(false);
        bottomBar.setBorder(new EmptyBorder(5, 10, 5, 10));
        bottomBar.add(saveBtn);

        panel.add(controls,              BorderLayout.NORTH);
        panel.add(new JScrollPane(marksTable), BorderLayout.CENTER);
        panel.add(bottomBar,             BorderLayout.SOUTH);
        return panel;
    }

    // ── Tab 2: View Results ──────────────────────────────────
    private JPanel buildViewResultsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 250, 252));

        // Controls
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.setBorder(new EmptyBorder(10, 10, 5, 10));
        controls.setOpaque(false);

        List<Student> students = studentService.getAllStudents();
        JComboBox<Student> studentBox = new JComboBox<>(students.toArray(new Student[0]));
        studentBox.setPreferredSize(new Dimension(250, 32));
        studentBox.setFont(new Font("Arial", Font.PLAIN, 13));

        JButton viewBtn = new JButton("View Results");
        viewBtn.setBackground(new Color(37, 99, 235));
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFocusPainted(false);

        JLabel cgpaLabel = new JLabel("CGPA: —");
        cgpaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cgpaLabel.setForeground(new Color(26, 58, 92));

        controls.add(new JLabel("Select Student: "));
        controls.add(studentBox);
        controls.add(viewBtn);
        controls.add(Box.createHorizontalStrut(20));
        controls.add(cgpaLabel);

        // Results table
        String[] cols = {"Course", "Exam Type", "Marks", "Total", "Grade"};
        DefaultTableModel resultModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable resultTable = new JTable(resultModel);
        resultTable.setFont(new Font("Arial", Font.PLAIN, 13));
        resultTable.setRowHeight(28);
        resultTable.getTableHeader().setBackground(new Color(26, 58, 92));
        resultTable.getTableHeader().setForeground(Color.WHITE);
        resultTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        viewBtn.addActionListener(e -> {
            Student selected = (Student) studentBox.getSelectedItem();
            if (selected == null) return;
            resultModel.setRowCount(0);

            List<Mark> marks = resultService.getMarksByStudent(selected.getStudentId());
            double cgpa = resultService.computeCGPA(selected.getStudentId());
            cgpaLabel.setText("CGPA: " + cgpa);

            for (Mark m : marks) {
                resultModel.addRow(new Object[]{
                        m.getCourseName(),
                        m.getExamType(),
                        m.getMarksObtained(),
                        m.getTotalMarks(),
                        m.getGrade()
                });
            }
        });

        panel.add(controls,               BorderLayout.NORTH);
        panel.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        return panel;
    }
}