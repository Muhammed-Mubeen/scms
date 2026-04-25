package com.scms.servlet;

import com.scms.model.Student;
import com.scms.model.User;
import com.scms.model.Fee;
import com.scms.service.AttendanceService;
import com.scms.service.FeeService;
import com.scms.service.ResultService;
import com.scms.service.StudentService;
import com.scms.util.LogUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {

    private final StudentService    studentService    = new StudentService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final ResultService     resultService     = new ResultService();
    private final FeeService        feeService        = new FeeService();
    private static final Logger logger = LogUtil.getLogger(StudentDashboardServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        try {
            // Get student by user_id
            Student student = studentService.getStudentByUserId(loggedUser.getUserId());

            if (student == null) {
                logger.warn("Student not found for user ID: {}", loggedUser.getUserId());
                req.setAttribute("student", new Student());
                req.setAttribute("attendanceValue", 0.0);
                req.setAttribute("attendance", "0.0");
                req.setAttribute("cgpa", "0.00");
                req.setAttribute("marks", new java.util.ArrayList<>());
                req.setAttribute("attendanceMap", new HashMap<>());
                req.setAttribute("totalDue", 0.0);
                req.getRequestDispatcher("/WEB-INF/views/student/dashboard.jsp")
                        .forward(req, resp);
                return;
            }

            // Get attendance percentage
            Map<String, Double> attendanceMap =
                    attendanceService.getAttendancePercentage(student.getStudentId());
            double overallAttendance = attendanceMap.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .average().orElse(0.0);

            // Get CGPA
            double cgpa = resultService.computeCGPA(student.getStudentId());

            // Get marks
            var marks = resultService.getMarksByStudent(student.getStudentId());

            // Get fees
            var fees = feeService.getFeesByStudent(student.getStudentId());
            double totalDue = fees.stream().mapToDouble(Fee::getDue).sum();

            logger.info("Student {} accessed dashboard", student.getRollNumber());

            // Set attributes as numbers and formatted strings
            req.setAttribute("student", student);
            req.setAttribute("attendanceValue", overallAttendance);  // ← Pass as number
            req.setAttribute("attendance", String.format("%.1f", overallAttendance));  // ← For display
            req.setAttribute("cgpa", String.format("%.2f", cgpa));
            req.setAttribute("marks", marks);
            req.setAttribute("totalDue", totalDue);
            req.setAttribute("attendanceMap", attendanceMap);

        } catch (Exception e) {
            logger.error("Error in StudentDashboardServlet: ", e);
            req.setAttribute("error", "An error occurred while loading dashboard.");
            req.setAttribute("student", new Student());
            req.setAttribute("attendanceValue", 0.0);
            req.setAttribute("attendance", "0.0");
            req.setAttribute("cgpa", "0.00");
            req.setAttribute("marks", new java.util.ArrayList<>());
            req.setAttribute("attendanceMap", new HashMap<>());
            req.setAttribute("totalDue", 0.0);
        }

        req.getRequestDispatcher("/WEB-INF/views/student/dashboard.jsp")
                .forward(req, resp);
    }
}