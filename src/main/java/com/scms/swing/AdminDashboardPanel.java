package com.scms.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminDashboardPanel extends JPanel {

    private final MainFrame mainFrame;

    public AdminDashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        // ── Top navbar ──────────────────────────────────────────
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(new Color(26, 58, 92));
        navbar.setPreferredSize(new Dimension(0, 55));
        navbar.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel appName = new JLabel("🎓 SCMS — Admin Panel");
        appName.setFont(new Font("Arial", Font.BOLD, 18));
        appName.setForeground(Color.WHITE);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> mainFrame.showPanel("LOGIN"));

        navbar.add(appName,    BorderLayout.WEST);
        navbar.add(logoutBtn,  BorderLayout.EAST);

        // ── Tabbed pane with real panels ────────────────────────
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 13));

        tabs.addTab("🏛 Departments", new DepartmentPanel());
        tabs.addTab("👤 Students",   new StudentPanel());
        tabs.addTab("📚 Courses",    new CoursePanel());
        tabs.addTab("📋 Attendance",  new AttendancePanel());
        tabs.addTab("📊 Marks",       new MarksPanel());
        tabs.addTab("📋 Attendance", buildStubPanel("Attendance — Coming Day 4"));
        tabs.addTab("📊 Marks",      buildStubPanel("Marks — Coming Day 4"));
        tabs.addTab("💰 Fees",       buildStubPanel("Fees — Coming Day 5"));
        tabs.addTab("🔔 Notices",    buildStubPanel("Notices — Coming Day 5"));
        tabs.addTab("📈 Reports",    buildStubPanel("Reports — Coming Day 6"));

        add(navbar, BorderLayout.NORTH);
        add(tabs,   BorderLayout.CENTER);
    }

    private JPanel buildStubPanel(String message) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(248, 250, 252));
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.GRAY);
        panel.add(label);
        return panel;
    }
}