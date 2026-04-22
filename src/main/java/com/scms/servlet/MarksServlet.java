package com.scms.servlet;

import com.scms.model.Exam;
import com.scms.model.Student;
import com.scms.service.ResultService;
import com.scms.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/marks")
public class MarksServlet extends HttpServlet {

    private final ResultService  resultService  = new ResultService();
    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "enter" -> {
                // Show marks entry form for an exam
                int examId = Integer.parseInt(req.getParameter("examId"));
                Exam exam  = resultService.getExamById(examId);

                List<Student>  students    = resultService.getAllStudents();
                List<com.scms.model.Mark> existingMarks = resultService.getMarksByExam(examId);

                // Pre-fill map
                Map<Integer, Double> existingMap = new HashMap<>();
                for (com.scms.model.Mark m : existingMarks) {
                    existingMap.put(m.getStudentId(), m.getMarksObtained());
                }

                req.setAttribute("exam",        exam);
                req.setAttribute("students",    students);
                req.setAttribute("existingMap", existingMap);
                req.getRequestDispatcher("/WEB-INF/views/admin/enter-marks.jsp")
                        .forward(req, resp);
            }

            case "results" -> {
                // View results for a student
                int studentId = Integer.parseInt(req.getParameter("studentId"));
                req.setAttribute("marks",    resultService.getMarksByStudent(studentId));
                req.setAttribute("cgpa",     resultService.computeCGPA(studentId));
                req.setAttribute("student",  studentService.getStudentById(studentId));
                req.getRequestDispatcher("/WEB-INF/views/admin/results.jsp")
                        .forward(req, resp);
            }

            default -> {
                // List all exams
                req.setAttribute("exams",    resultService.getAllExams());
                req.setAttribute("students", resultService.getAllStudents());
                req.getRequestDispatcher("/WEB-INF/views/admin/marks-home.jsp")
                        .forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int examId = Integer.parseInt(req.getParameter("examId"));
        String[] studentIds = req.getParameterValues("studentIds");

        Map<Integer, Double> marksMap = new HashMap<>();

        if (studentIds != null) {
            for (String sid : studentIds) {
                int    id          = Integer.parseInt(sid);
                String marksParam  = req.getParameter("marks_" + id);
                double marks       = 0.0;
                try {
                    marks = Double.parseDouble(marksParam);
                } catch (NumberFormatException ignored) {}
                marksMap.put(id, marks);
            }
        }

        try {
            resultService.saveMarks(examId, marksMap);
            req.getSession().setAttribute("successMsg", "Marks saved successfully!");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMsg", "Error: " + e.getMessage());
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/marks");
    }
}