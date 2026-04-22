package com.scms.service;

import com.scms.dao.ExamDAO;
import com.scms.dao.MarkDAO;
import com.scms.dao.StudentDAO;
import com.scms.model.Exam;
import com.scms.model.Mark;
import com.scms.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultService {

    private final MarkDAO    markDAO    = new MarkDAO();
    private final ExamDAO    examDAO    = new ExamDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    // Compute grade from percentage
    public String computeGrade(double obtained, int total) {
        if (total == 0) return "F";
        double pct = (obtained / total) * 100;
        if (pct >= 90) return "O";
        if (pct >= 80) return "A+";
        if (pct >= 70) return "A";
        if (pct >= 60) return "B+";
        if (pct >= 50) return "B";
        if (pct >= 40) return "C";
        return "F";
    }

    // Compute CGPA for a student
    // Uses HashMap<courseId, List<Mark>> to group marks by course
    public double computeCGPA(int studentId) {
        List<Mark> marks = markDAO.findByStudent(studentId);

        // Group marks by course
        Map<String, List<Double>> courseMarks = new HashMap<>();
        Map<String, Integer>      courseTotals = new HashMap<>();

        for (Mark m : marks) {
            String course = m.getCourseName();
            courseMarks.computeIfAbsent(course, k -> new java.util.ArrayList<>())
                    .add(m.getMarksObtained());
            courseTotals.put(course, m.getTotalMarks());
        }

        if (courseMarks.isEmpty()) return 0.0;

        double totalGradePoints = 0.0;
        int    courseCount      = 0;

        for (String course : courseMarks.keySet()) {
            List<Double> obtained = courseMarks.get(course);
            int total = courseTotals.getOrDefault(course, 100);

            double avg = obtained.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            double pct = (avg / total) * 100;
            totalGradePoints += pctToGradePoint(pct);
            courseCount++;
        }

        double cgpa = totalGradePoints / courseCount;
        return Math.round(cgpa * 100.0) / 100.0;
    }

    // Convert percentage to grade point (10-point scale)
    private double pctToGradePoint(double pct) {
        if (pct >= 90) return 10.0;
        if (pct >= 80) return 9.0;
        if (pct >= 70) return 8.0;
        if (pct >= 60) return 7.0;
        if (pct >= 50) return 6.0;
        if (pct >= 40) return 5.0;
        return 0.0;
    }

    // Save marks for all students in an exam
    public boolean saveMarks(int examId, Map<Integer, Double> studentMarksMap) {
        Exam exam = examDAO.findById(examId);
        if (exam == null) return false;

        for (Map.Entry<Integer, Double> entry : studentMarksMap.entrySet()) {
            Mark m = new Mark();
            m.setExamId(examId);
            m.setStudentId(entry.getKey());
            m.setMarksObtained(entry.getValue());
            m.setGrade(computeGrade(entry.getValue(), exam.getTotalMarks()));
            markDAO.saveOrUpdate(m);
        }
        return true;
    }

    // Get all marks for an exam
    public List<Mark> getMarksByExam(int examId) {
        return markDAO.findByExam(examId);
    }

    // Get all marks for a student
    public List<Mark> getMarksByStudent(int studentId) {
        return markDAO.findByStudent(studentId);
    }

    // Get all exams
    public List<Exam> getAllExams() {
        return examDAO.findAll();
    }

    // Get exam by ID
    public Exam getExamById(int examId) {
        return examDAO.findById(examId);
    }

    // Schedule a new exam
    public boolean scheduleExam(Exam exam) {
        if (exam.getTotalMarks() <= 0) {
            throw new IllegalArgumentException("Total marks must be greater than 0.");
        }
        return examDAO.save(exam);
    }

    // Delete exam
    public boolean deleteExam(int examId) {
        return examDAO.delete(examId);
    }

    // Get all students (for marks entry)
    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }
}
