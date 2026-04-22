package com.scms.service;

import com.scms.dao.ExamDAO;
import com.scms.model.Exam;

import java.util.List;

public class ExamService {

    private final ExamDAO examDAO = new ExamDAO();

    public List<Exam> getAllExams() {
        return examDAO.findAll();
    }

    public List<Exam> getExamsByCourse(int courseId) {
        return examDAO.findByCourse(courseId);
    }

    public Exam getExamById(int id) {
        return examDAO.findById(id);
    }

    public boolean scheduleExam(Exam exam) {
        if (exam.getTotalMarks() <= 0) {
            throw new IllegalArgumentException("Total marks must be greater than 0.");
        }
        if (exam.getAcademicYear() == null || exam.getAcademicYear().isBlank()) {
            throw new IllegalArgumentException("Academic year is required.");
        }
        return examDAO.save(exam);
    }

    public boolean deleteExam(int examId) {
        return examDAO.delete(examId);
    }
}