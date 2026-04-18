package com.scms.servlet;

import com.scms.model.Student;
import com.scms.service.CourseService;
import com.scms.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/students")
public class StudentServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final CourseService  courseService  = new CourseService();

    // LIST all students
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add" -> {
                // Show add form
                req.setAttribute("departments", courseService.getAllDepartments());
                req.getRequestDispatcher("/WEB-INF/views/admin/add-student.jsp")
                        .forward(req, resp);
            }
            case "edit" -> {
                // Show edit form
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("student", studentService.getStudentById(id));
                req.setAttribute("departments", courseService.getAllDepartments());
                req.getRequestDispatcher("/WEB-INF/views/admin/edit-student.jsp")
                        .forward(req, resp);
            }
            case "delete" -> {
                // Delete and redirect
                int id = Integer.parseInt(req.getParameter("id"));
                studentService.deleteStudent(id);
                resp.sendRedirect(req.getContextPath() + "/admin/students");
            }
            default -> {
                // List all
                req.setAttribute("students", studentService.getAllStudents());
                req.getRequestDispatcher("/WEB-INF/views/admin/student-list.jsp")
                        .forward(req, resp);
            }
        }
    }

    // ADD or UPDATE student
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                Student s = extractStudent(req);
                String password = req.getParameter("password");
                studentService.addStudent(s, password);
                req.getSession().setAttribute("successMsg", "Student added successfully!");

            } else if ("edit".equals(action)) {
                Student s = extractStudent(req);
                s.setStudentId(Integer.parseInt(req.getParameter("studentId")));
                studentService.updateStudent(s);
                req.getSession().setAttribute("successMsg", "Student updated successfully!");
            }

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("errorMsg", e.getMessage());
        } catch (Exception e) {
            req.getSession().setAttribute("errorMsg", "Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/students");
    }

    // Helper — extract Student from form fields
    private Student extractStudent(HttpServletRequest req) {
        Student s = new Student();
        s.setRollNumber(req.getParameter("rollNumber").trim());
        s.setName(req.getParameter("name").trim());
        s.setEmail(req.getParameter("email").trim());
        s.setPhone(req.getParameter("phone").trim());
        s.setDepartmentId(Integer.parseInt(req.getParameter("departmentId")));
        s.setSemester(Integer.parseInt(req.getParameter("semester")));
        s.setBatchYear(Integer.parseInt(req.getParameter("batchYear")));
        return s;
    }
}