package com.scms.swing;

import com.scms.model.Department;
import com.scms.model.Notice;
import com.scms.service.NoticeService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NoticePanel extends JPanel {

    private final NoticeService noticeService = new NoticeService();

    private JTable            table;
    private DefaultTableModel tableModel;

    public NoticePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));
        buildUI();
        loadNotices();
    }

    private void buildUI() {
        // ── Top bar ──────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(248, 250, 252));
        topBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("🔔 Notice Board");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(26, 58, 92));

        JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBar.setOpaque(false);

        JButton postBtn    = new JButton("+ Post Notice");
        JButton refreshBtn = new JButton("↻ Refresh");

        postBtn.setBackground(new Color(26, 58, 92));
        postBtn.setForeground(Color.WHITE);
        postBtn.setFocusPainted(false);

        postBtn.addActionListener(e -> showPostDialog());
        refreshBtn.addActionListener(e -> loadNotices());

        rightBar.add(postBtn);
        rightBar.add(refreshBtn);

        topBar.add(title,    BorderLayout.WEST);
        topBar.add(rightBar, BorderLayout.EAST);

        // ── Table ────────────────────────────────────────────
        String[] cols = {"notice_id", "Title", "Department",
                "Posted By", "Date"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(26, 58, 92));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));

        // Hide notice_id column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        // Double-click to view full content
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) viewNoticeContent();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // ── Bottom bar ───────────────────────────────────────
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomBar.setOpaque(false);
        bottomBar.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton viewBtn   = new JButton("👁 View");
        JButton deleteBtn = new JButton("🗑 Delete");

        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);

        viewBtn.addActionListener(e -> viewNoticeContent());
        deleteBtn.addActionListener(e -> deleteSelected());

        bottomBar.add(viewBtn);
        bottomBar.add(deleteBtn);

        // ── Assemble ─────────────────────────────────────────
        add(topBar,     BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomBar,  BorderLayout.SOUTH);
    }

    public void loadNotices() {
        tableModel.setRowCount(0);
        List<Notice> notices = noticeService.getAllNotices();
        for (Notice n : notices) {
            tableModel.addRow(new Object[]{
                    n.getNoticeId(),
                    n.getTitle(),
                    n.getDepartmentName() != null
                            ? n.getDepartmentName() : "All Departments",
                    n.getPostedByName(),
                    n.getCreatedAt().toLocalDate()
            });
        }
    }

    // ── View full notice content ─────────────────────────────
    private void viewNoticeContent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a notice to view.");
            return;
        }

        int    noticeId = (int)    tableModel.getValueAt(selectedRow, 0);
        String title    = (String) tableModel.getValueAt(selectedRow, 1);

        // Find content from service
        List<Notice> notices = noticeService.getAllNotices();
        Notice found = notices.stream()
                .filter(n -> n.getNoticeId() == noticeId)
                .findFirst().orElse(null);

        if (found == null) return;

        JTextArea contentArea = new JTextArea(found.getContent());
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 13));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane sp = new JScrollPane(contentArea);
        sp.setPreferredSize(new Dimension(450, 200));

        JOptionPane.showMessageDialog(this, sp,
                "📢 " + title, JOptionPane.INFORMATION_MESSAGE);
    }

    // ── Post notice dialog ───────────────────────────────────
    private void showPostDialog() {
        List<Department> depts = noticeService.getAllDepartments();

        JTextField titleField   = new JTextField();
        JTextArea  contentArea  = new JTextArea(4, 20);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane contentScroll = new JScrollPane(contentArea);

        // Department dropdown — first item = All Departments
        Department[] deptArray = new Department[depts.size() + 1];
        deptArray[0] = new Department("-- All Departments (Broadcast) --");
        for (int i = 0; i < depts.size(); i++) {
            deptArray[i + 1] = depts.get(i);
        }
        JComboBox<Department> deptBox = new JComboBox<>(deptArray);

        JPanel form = new JPanel(new GridLayout(6, 1, 5, 5));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.setPreferredSize(new Dimension(420, 260));

        form.add(new JLabel("Title:"));
        form.add(titleField);
        form.add(new JLabel("Content:"));
        form.add(contentScroll);
        form.add(new JLabel("Department:"));
        form.add(deptBox);

        int result = JOptionPane.showConfirmDialog(
                this, form, "Post New Notice",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Notice notice = new Notice();
                notice.setTitle(titleField.getText().trim());
                notice.setContent(contentArea.getText().trim());
                notice.setPostedBy(1); // admin user_id = 1

                Department selectedDept =
                        (Department) deptBox.getSelectedItem();
                if (selectedDept != null && selectedDept.getDeptId() != 0) {
                    notice.setDepartmentId(selectedDept.getDeptId());
                }

                noticeService.postNotice(notice);
                loadNotices();
                JOptionPane.showMessageDialog(this,
                        "✅ Notice posted successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── Delete notice ────────────────────────────────────────
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a notice to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, "Delete this notice?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int noticeId = (int) tableModel.getValueAt(selectedRow, 0);
            noticeService.deleteNotice(noticeId);
            loadNotices();
            JOptionPane.showMessageDialog(this, "✅ Notice deleted.");
        }
    }
}