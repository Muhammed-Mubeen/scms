package com.scms.servlet;

import com.scms.model.Attendance;
import com.scms.model.Student;
import com.scms.model.User;
import com.scms.service.AttendanceService;
import com.scms.service.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/attendance")
public class AttendanceServlet extends HttpServlet {

    private final AttendanceService attendanceService = new AttendanceService();
    private final CourseService     courseService     = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "mark" -> {
                // Show mark attendance form
                String courseIdParam = req.getParameter("courseId");
                String dateParam     = req.getParameter("date");

                req.setAttribute("courses", courseService.getAllCourses());

                if (courseIdParam != null && dateParam != null) {
                    int       courseId = Integer.parseInt(courseIdParam);
                    LocalDate date     = LocalDate.parse(dateParam);

                    List<Student> students = attendanceService
                            .getStudentsForCourse(courseId);
                    List<Attendance> existing = attendanceService
                            .getAttendanceByCoursAndDate(courseId, date);

                    // Build map of existing status for pre-filling
                    Map<Integer, String> existingMap = new HashMap<>();
                    for (Attendance a : existing) {
                        existingMap.put(a.getStudentId(), a.getStatus());
                    }

                    req.setAttribute("students",    students);
                    req.setAttribute("existingMap", existingMap);
                    req.setAttribute("courseId",    courseId);
                    req.setAttribute("date",        dateParam);
                }

                req.getRequestDispatcher("/WEB-INF/views/admin/mark-attendance.jsp")
                        .forward(req, resp);
            }

            case "view" -> {
                // View attendance for a student
                int studentId = Integer.parseInt(req.getParameter("studentId"));
                req.setAttribute("attendanceList",
                        attendanceService.getStudentAttendance(studentId));
                req.setAttribute("percentageMap",
                        attendanceService.getAttendancePercentage(studentId));
                req.getRequestDispatcher("/WEB-INF/views/admin/view-attendance.jsp")
                        .forward(req, resp);
            }

            default -> {
                // List — show all courses to pick from
                req.setAttribute("courses", courseService.getAllCourses());
                req.getRequestDispatcher("/WEB-INF/views/admin/attendance-home.jsp")
                        .forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int       courseId = Integer.parseInt(req.getParameter("courseId"));
        LocalDate date     = LocalDate.parse(req.getParameter("date"));

        HttpSession session  = req.getSession();
        User loggedUser      = (User) session.getAttribute("loggedUser");
        int  markedBy        = loggedUser.getUserId();

        // Build status map from checkboxes
        // Each student has a radio: studentStatus_<id> = P / A / L
        Map<Integer, String> statusMap = new HashMap<>();
        String[] studentIds = req.getParameterValues("studentIds");

        if (studentIds != null) {
            for (String sid : studentIds) {
                int    id     = Integer.parseInt(sid);
                String status = req.getParameter("status_" + id);
                if (status == null) status = "A"; // default absent
                statusMap.put(id, status);
            }
        }

        try {
            attendanceService.markAttendance(courseId, date, statusMap, markedBy);
            req.getSession().setAttribute("successMsg", "Attendance marked successfully!");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMsg", "Error: " + e.getMessage());
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/attendance");
    }
}