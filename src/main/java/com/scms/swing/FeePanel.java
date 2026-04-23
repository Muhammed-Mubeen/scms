package com.scms.swing;

import com.scms.model.Fee;
import com.scms.model.Student;
import com.scms.service.FeeService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class FeePanel extends JPanel {

    private final FeeService feeService = new FeeService();

    private JTable            table;
    private DefaultTableModel tableModel;
    private JLabel            totalCollectedLabel;
    private JLabel            totalDuesLabel;

    public FeePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));
        buildUI();
        loadFees();
    }

    private void buildUI() {
        // ── Top bar ──────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(248, 250, 252));
        topBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("💰 Fee Management");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(26, 58, 92));

        JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBar.setOpaque(false);

        JButton addBtn      = new JButton("+ Add Fee");
        JButton pendingBtn  = new JButton("⚠️ Pending");
        JButton allBtn      = new JButton("↻ All Fees");

        addBtn.setBackground(new Color(26, 58, 92));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);

        pendingBtn.setBackground(new Color(220, 175, 0));
        pendingBtn.setForeground(Color.WHITE);
        pendingBtn.setFocusPainted(false);

        addBtn.addActionListener(e -> showAddFeeDialog());
        pendingBtn.addActionListener(e -> loadPendingFees());
        allBtn.addActionListener(e -> loadFees());

        rightBar.add(addBtn);
        rightBar.add(pendingBtn);
        rightBar.add(allBtn);

        topBar.add(title,    BorderLayout.WEST);
        topBar.add(rightBar, BorderLayout.EAST);

        // ── Summary panel ────────────────────────────────────
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        summaryPanel.setBackground(new Color(240, 247, 255));
        summaryPanel.setBorder(new EmptyBorder(8, 10, 8, 10));

        totalCollectedLabel = new JLabel("Total Collected: ₹0.00");
        totalCollectedLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalCollectedLabel.setForeground(new Color(22, 101, 52));

        totalDuesLabel = new JLabel("   Total Dues: ₹0.00");
        totalDuesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalDuesLabel.setForeground(new Color(153, 27, 27));

        summaryPanel.add(totalCollectedLabel);
        summaryPanel.add(totalDuesLabel);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setOpaque(false);
        northPanel.add(topBar,       BorderLayout.NORTH);
        northPanel.add(summaryPanel, BorderLayout.SOUTH);

        // ── Table ────────────────────────────────────────────
        String[] cols = {"fee_id", "Student", "Roll No",
                "Amount", "Paid", "Due",
                "Due Date", "Semester", "Status"};
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

        // Hide fee_id column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);

        // ── Bottom bar ───────────────────────────────────────
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomBar.setOpaque(false);
        bottomBar.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton payBtn = new JButton("💳 Record Payment");
        payBtn.setBackground(new Color(22, 101, 52));
        payBtn.setForeground(Color.WHITE);
        payBtn.setFocusPainted(false);
        payBtn.addActionListener(e -> showPaymentDialog());

        bottomBar.add(payBtn);

        // ── Assemble ─────────────────────────────────────────
        add(northPanel,  BorderLayout.NORTH);
        add(scrollPane,  BorderLayout.CENTER);
        add(bottomBar,   BorderLayout.SOUTH);
    }

    // ── Load all fees ────────────────────────────────────────
    public void loadFees() {
        tableModel.setRowCount(0);
        List<Fee> fees = feeService.getAllFees();
        populateTable(fees);
        updateSummary();
    }

    // ── Load pending fees only ───────────────────────────────
    private void loadPendingFees() {
        tableModel.setRowCount(0);
        List<Fee> fees = feeService.getPendingFees();
        populateTable(fees);
    }

    private void populateTable(List<Fee> fees) {
        for (Fee f : fees) {
            tableModel.addRow(new Object[]{
                    f.getFeeId(),
                    f.getStudentName(),
                    f.getRollNumber(),
                    "₹" + f.getAmount(),
                    "₹" + f.getPaidAmount(),
                    "₹" + f.getDue(),
                    f.getDueDate(),
                    f.getSemester(),
                    f.getStatus().toUpperCase()
            });
        }
    }

    private void updateSummary() {
        totalCollectedLabel.setText(
                "Total Collected: ₹" + String.format("%.2f",
                        feeService.getTotalCollected()));
        totalDuesLabel.setText(
                "   Total Dues: ₹" + String.format("%.2f",
                        feeService.getTotalDues()));
    }

    // ── Add fee dialog ───────────────────────────────────────
    private void showAddFeeDialog() {
        List<Student> students = feeService.getAllStudents();

        JComboBox<Student> studentBox =
                new JComboBox<>(students.toArray(new Student[0]));
        JTextField amountField      = new JTextField();
        JTextField dueDateField     = new JTextField(
                LocalDate.now().plusMonths(1).toString());
        JSpinner semesterSpinner    =
                new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        JTextField academicYearField = new JTextField("2024-25");

        JPanel form = buildFormPanel(
                new String[]{"Student", "Amount (₹)",
                        "Due Date (yyyy-MM-dd)",
                        "Semester", "Academic Year"},
                new JComponent[]{studentBox, amountField,
                        dueDateField, semesterSpinner,
                        academicYearField}
        );

        int result = JOptionPane.showConfirmDialog(
                this, form, "Add Fee Record",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Fee fee = new Fee();
                fee.setStudentId(
                        ((Student) studentBox.getSelectedItem()).getStudentId());
                fee.setAmount(
                        Double.parseDouble(amountField.getText().trim()));
                fee.setDueDate(
                        LocalDate.parse(dueDateField.getText().trim()));
                fee.setSemester((Integer) semesterSpinner.getValue());
                fee.setAcademicYear(academicYearField.getText().trim());

                feeService.addFee(fee);
                loadFees();
                JOptionPane.showMessageDialog(this,
                        "✅ Fee record added successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── Payment dialog ───────────────────────────────────────
    private void showPaymentDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a fee record to pay.");
            return;
        }

        int    feeId  = (int) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 8);

        if ("PAID".equals(status)) {
            JOptionPane.showMessageDialog(this,
                    "This fee is already fully paid.");
            return;
        }

        Fee fee = feeService.getFeeById(feeId);

        // Show summary in dialog
        String info = String.format(
                "Student : %s (%s)%n" +
                        "Total   : ₹%.2f%n" +
                        "Paid    : ₹%.2f%n" +
                        "Due     : ₹%.2f",
                fee.getStudentName(), fee.getRollNumber(),
                fee.getAmount(), fee.getPaidAmount(), fee.getDue()
        );

        JTextArea infoArea = new JTextArea(info);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        infoArea.setBackground(new Color(240, 247, 255));
        infoArea.setBorder(new EmptyBorder(8, 8, 8, 8));

        JTextField paymentField = new JTextField();

        JPanel form = new JPanel(new BorderLayout(0, 10));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.setPreferredSize(new Dimension(380, 180));
        form.add(infoArea, BorderLayout.NORTH);

        JPanel payRow = new JPanel(new GridLayout(1, 2, 8, 0));
        payRow.add(new JLabel("Payment Amount (₹):"));
        payRow.add(paymentField);
        form.add(payRow, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                this, form, "Record Payment",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double payment =
                        Double.parseDouble(paymentField.getText().trim());
                feeService.recordPayment(feeId, payment);
                loadFees();
                JOptionPane.showMessageDialog(this,
                        "✅ Payment recorded successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel buildFormPanel(String[] labels, JComponent[] fields) {
        JPanel panel = new JPanel(new GridLayout(labels.length, 2, 8, 8));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(420, labels.length * 42));
        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i] + ":"));
            panel.add(fields[i]);
        }
        return panel;
    }
}