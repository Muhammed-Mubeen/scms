package com.scms.model;

public class Mark {
    private int markId;
    private int examId;
    private int studentId;
    private String studentName;   // for display
    private String rollNumber;    // for display
    private double marksObtained;
    private String grade;
    private String examType;      // for display
    private String courseName;    // for display
    private int totalMarks;       // for display

    public Mark() {}

    public int getMarkId()                          { return markId; }
    public void setMarkId(int markId)               { this.markId = markId; }

    public int getExamId()                          { return examId; }
    public void setExamId(int examId)               { this.examId = examId; }

    public int getStudentId()                       { return studentId; }
    public void setStudentId(int studentId)         { this.studentId = studentId; }

    public String getStudentName()                  { return studentName; }
    public void setStudentName(String studentName)  { this.studentName = studentName; }

    public String getRollNumber()                   { return rollNumber; }
    public void setRollNumber(String rollNumber)    { this.rollNumber = rollNumber; }

    public double getMarksObtained()                { return marksObtained; }
    public void setMarksObtained(double marksObtained){ this.marksObtained = marksObtained; }

    public String getGrade()                        { return grade; }
    public void setGrade(String grade)              { this.grade = grade; }

    public String getExamType()                     { return examType; }
    public void setExamType(String examType)        { this.examType = examType; }

    public String getCourseName()                   { return courseName; }
    public void setCourseName(String courseName)    { this.courseName = courseName; }

    public int getTotalMarks()                      { return totalMarks; }
    public void setTotalMarks(int totalMarks)       { this.totalMarks = totalMarks; }
}