package com.scms.servlet;

import com.scms.model.Fee;
import com.scms.model.User;
import com.scms.service.FeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/fees")
public class FeeServlet extends HttpServlet {

    private final FeeService feeService = new FeeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "add" -> {
                req.setAttribute("students", feeService.getAllStudents());
                req.getRequestDispatcher("/WEB-INF/views/admin/add-fee.jsp")
                        .forward(req, resp);
            }

            case "pay" -> {
                // Show payment dialog page
                int feeId = Integer.parseInt(req.getParameter("feeId"));
                req.setAttribute("fee", feeService.getFeeById(feeId));
                req.getRequestDispatcher("/WEB-INF/views/admin/pay-fee.jsp")
                        .forward(req, resp);
            }

            case "student" -> {
                // View fees for one student
                int studentId = Integer.parseInt(req.getParameter("studentId"));
                req.setAttribute("fees",
                        feeService.getFeesByStudent(studentId));
                req.setAttribute("students", feeService.getAllStudents());
                req.getRequestDispatcher("/WEB-INF/views/admin/fee-dashboard.jsp")
                        .forward(req, resp);
            }

            case "pending" -> {
                req.setAttribute("fees",           feeService.getPendingFees());
                req.setAttribute("students",       feeService.getAllStudents());
                req.setAttribute("totalDues",      feeService.getTotalDues());
                req.setAttribute("totalCollected", feeService.getTotalCollected());
                req.getRequestDispatcher("/WEB-INF/views/admin/fee-dashboard.jsp")
                        .forward(req, resp);
            }

            default -> {
                // List all fees
                req.setAttribute("fees",           feeService.getAllFees());
                req.setAttribute("students",       feeService.getAllStudents());
                req.setAttribute("totalDues",      feeService.getTotalDues());
                req.setAttribute("totalCollected", feeService.getTotalCollected());
                req.getRequestDispatcher("/WEB-INF/views/admin/fee-dashboard.jsp")
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
                Fee fee = new Fee();
                fee.setStudentId(Integer.parseInt(req.getParameter("studentId")));
                fee.setAmount(Double.parseDouble(req.getParameter("amount")));
                fee.setDueDate(LocalDate.parse(req.getParameter("dueDate")));
                fee.setSemester(Integer.parseInt(req.getParameter("semester")));
                fee.setAcademicYear(req.getParameter("academicYear"));

                feeService.addFee(fee);
                req.getSession().setAttribute("successMsg",
                        "Fee record added successfully!");

            } else if ("pay".equals(action)) {
                int    feeId   = Integer.parseInt(req.getParameter("feeId"));
                double payment = Double.parseDouble(req.getParameter("paymentAmount"));

                feeService.recordPayment(feeId, payment);
                req.getSession().setAttribute("successMsg",
                        "Payment recorded successfully!");
            }

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("errorMsg", e.getMessage());
        } catch (Exception e) {
            req.getSession().setAttribute("errorMsg",
                    "Error: " + e.getMessage());
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/fees");
    }
}