package com.scms.servlet;

import com.scms.model.User;
import com.scms.service.CourseService;
import com.scms.util.LogUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/faculty/dashboard")
public class FacultyDashboardServlet extends HttpServlet {

    private final CourseService courseService = new CourseService();
    private static final Logger logger = LogUtil.getLogger(FacultyDashboardServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        try {
            var courses = courseService.getAllCourses();
            logger.info("Faculty {} accessed dashboard - {} courses available",
                    loggedUser.getUsername(), courses.size());

            req.setAttribute("courses", courses);
            req.setAttribute("courseCount", courses.size());

        } catch (Exception e) {
            logger.error("Error in FacultyDashboardServlet: ", e);
            req.setAttribute("error", "An error occurred while loading dashboard.");
        }

        req.getRequestDispatcher("/WEB-INF/views/faculty/dashboard.jsp")
                .forward(req, resp);
    }
}