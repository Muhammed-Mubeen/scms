package com.scms.servlet;

import com.scms.model.User;
import com.scms.service.AttendanceService;
import com.scms.service.ResultService;
import com.scms.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {

    private final StudentService    studentService    = new StudentService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final ResultService     resultService     = new ResultService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        // Get student info from user_id
        // Note: You'd need to add a method to find student by user_id
        // For now, using placeholder

        req.setAttribute("attendance", "85%");
        req.setAttribute("cgpa", "8.5");
        req.setAttribute("feesStatus", "Pending");

        req.getRequestDispatcher("/WEB-INF/views/student/dashboard.jsp")
                .forward(req, resp);
    }
}