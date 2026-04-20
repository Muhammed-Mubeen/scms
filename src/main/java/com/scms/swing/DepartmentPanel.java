package com.scms.swing;

import com.scms.model.Department;
import com.scms.service.DepartmentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentPanel extends JPanel {

    private final DepartmentService departmentService = new DepartmentService();

    private JTable table;
    private DefaultTableModel tableModel;

    public DepartmentPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));
        buildUI();
        loadDepartments();
    }

    private void buildUI() {
        // ── Top bar ──────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(248, 250, 252));
        topBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("🏛 Departments");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(26, 58, 92));

        JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBar.setOpaque(false);

        JButton addBtn     = new JButton("+ Add Department");
        JButton refreshBtn = new JButton("↻ Refresh");

        addBtn.setBackground(new Color(26, 58, 92));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);

        addBtn.addActionListener(e -> showAddDialog());
        refreshBtn.addActionListener(e -> loadDepartments());

        rightBar.add(addBtn);
        rightBar.add(refreshBtn);

        topBar.add(title,    BorderLayout.WEST);
        topBar.add(rightBar, BorderLayout.EAST);

        // ── Table ────────────────────────────────────────────
        String[] cols = {"ID", "Department Name"};
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

        // ── Assemble ─────────────────────────────────────────
        add(topBar,     BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadDepartments() {
        tableModel.setRowCount(0);
        List<Department> departments = departmentService.getAllDepartments();
        for (Department d : departments) {
            tableModel.addRow(new Object[]{
                    d.getDeptId(),
                    d.getDeptName()
            });
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();

        JPanel form = new JPanel(new GridLayout(1, 2, 8, 8));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.setPreferredSize(new Dimension(350, 40));
        form.add(new JLabel("Department Name:"));
        form.add(nameField);

        int result = JOptionPane.showConfirmDialog(
                this, form, "Add New Department",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                departmentService.addDepartment(name);
                loadDepartments();
                JOptionPane.showMessageDialog(this, "✅ Department added successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
