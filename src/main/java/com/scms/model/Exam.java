package com.scms.model;

import java.time.LocalDate;

public class Exam {
    private int examId;
    private int courseId;
    private String courseName;    // for display
    private String examType;      // mid, final, quiz
    private int totalMarks;
    private LocalDate examDate;
    private String academicYear;

    public Exam() {}

    public int getExamId()                          { return examId; }
    public void setExamId(int examId)               { this.examId = examId; }

    public int getCourseId()                        { return courseId; }
    public void setCourseId(int courseId)           { this.courseId = courseId; }

    public String getCourseName()                   { return courseName; }
    public void setCourseName(String courseName)    { this.courseName = courseName; }

    public String getExamType()                     { return examType; }
    public void setExamType(String examType)        { this.examType = examType; }

    public int getTotalMarks()                      { return totalMarks; }
    public void setTotalMarks(int totalMarks)       { this.totalMarks = totalMarks; }

    public LocalDate getExamDate()                  { return examDate; }
    public void setExamDate(LocalDate examDate)     { this.examDate = examDate; }

    public String getAcademicYear()                 { return academicYear; }
    public void setAcademicYear(String academicYear){ this.academicYear = academicYear; }
}