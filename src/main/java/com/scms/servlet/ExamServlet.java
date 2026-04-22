package com.scms.servlet;

import com.scms.model.Exam;
import com.scms.service.CourseService;
import com.scms.service.ExamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/exams")
public class ExamServlet extends HttpServlet {

    private final ExamService   examService   = new ExamService();
    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "add" -> {
                req.setAttribute("courses", courseService.getAllCourses());
                req.getRequestDispatcher("/WEB-INF/views/admin/add-exam.jsp")
                        .forward(req, resp);
            }

            case "delete" -> {
                int id = Integer.parseInt(req.getParameter("id"));
                examService.deleteExam(id);
                req.getSession().setAttribute("successMsg", "Exam deleted.");
                resp.sendRedirect(req.getContextPath() + "/admin/exams");
            }

            default -> {
                req.setAttribute("exams", examService.getAllExams());
                req.getRequestDispatcher("/WEB-INF/views/admin/exam-list.jsp")
                        .forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Exam exam = new Exam();
            exam.setCourseId(Integer.parseInt(req.getParameter("courseId")));
            exam.setExamType(req.getParameter("examType"));
            exam.setTotalMarks(Integer.parseInt(req.getParameter("totalMarks")));
            exam.setExamDate(LocalDate.parse(req.getParameter("examDate")));
            exam.setAcademicYear(req.getParameter("academicYear"));

            examService.scheduleExam(exam);
            req.getSession().setAttribute("successMsg", "Exam scheduled successfully!");

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("errorMsg", e.getMessage());
        } catch (Exception e) {
            req.getSession().setAttribute("errorMsg", "Error: " + e.getMessage());
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/exams");
    }
}