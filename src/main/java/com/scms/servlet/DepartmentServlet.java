package com.scms.servlet;

import com.scms.service.DepartmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/departments")
public class DepartmentServlet extends HttpServlet {

    private final DepartmentService departmentService = new DepartmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("departments", departmentService.getAllDepartments());
        req.getRequestDispatcher("/WEB-INF/views/admin/department-list.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("deptName");
        try {
            departmentService.addDepartment(name);
            req.getSession().setAttribute("successMsg", "Department added successfully!");
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("errorMsg", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin/departments");
    }
}