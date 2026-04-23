package com.scms.servlet;

import com.scms.model.User;
import com.scms.service.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/faculty/dashboard")
public class FacultyDashboardServlet extends HttpServlet {

    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        req.setAttribute("courses", courseService.getAllCourses());
        req.getRequestDispatcher("/WEB-INF/views/faculty/dashboard.jsp")
                .forward(req, resp);
    }
}