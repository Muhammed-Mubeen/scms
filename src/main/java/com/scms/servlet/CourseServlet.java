package com.scms.servlet;

import com.scms.model.Course;
import com.scms.service.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/courses")
public class CourseServlet extends HttpServlet {

    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add" -> {
                req.setAttribute("departments", courseService.getAllDepartments());
                req.getRequestDispatcher("/WEB-INF/views/admin/add-course.jsp")
                        .forward(req, resp);
            }
            case "edit" -> {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("course", courseService.getCourseById(id));
                req.setAttribute("departments", courseService.getAllDepartments());
                req.getRequestDispatcher("/WEB-INF/views/admin/edit-course.jsp")
                        .forward(req, resp);
            }
            case "delete" -> {
                int id = Integer.parseInt(req.getParameter("id"));
                courseService.deleteCourse(id);
                resp.sendRedirect(req.getContextPath() + "/admin/courses");
            }
            default -> {
                req.setAttribute("courses", courseService.getAllCourses());
                req.getRequestDispatcher("/WEB-INF/views/admin/course-list.jsp")
                        .forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                Course c = extractCourse(req);
                courseService.addCourse(c);
                req.getSession().setAttribute("successMsg", "Course added successfully!");

            } else if ("edit".equals(action)) {
                Course c = extractCourse(req);
                c.setCourseId(Integer.parseInt(req.getParameter("courseId")));
                courseService.updateCourse(c);
                req.getSession().setAttribute("successMsg", "Course updated successfully!");
            }

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("errorMsg", e.getMessage());
        } catch (Exception e) {
            req.getSession().setAttribute("errorMsg", "Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/courses");
    }

    private Course extractCourse(HttpServletRequest req) {
        Course c = new Course();
        c.setCourseCode(req.getParameter("courseCode").trim());
        c.setCourseName(req.getParameter("courseName").trim());
        c.setDepartmentId(Integer.parseInt(req.getParameter("departmentId")));
        c.setFacultyId(Integer.parseInt(req.getParameter("facultyId")));
        c.setCredits(Integer.parseInt(req.getParameter("credits")));
        c.setSemester(Integer.parseInt(req.getParameter("semester")));
        return c;
    }
}