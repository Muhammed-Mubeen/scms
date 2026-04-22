package com.scms.model;

import java.time.LocalDate;

public class Attendance {
    private int attendanceId;
    private int studentId;
    private String studentName;   // for display
    private String rollNumber;    // for display
    private int courseId;
    private String courseName;    // for display
    private LocalDate date;
    private String status;        // P, A, L
    private int markedBy;

    public Attendance() {}

    public int getAttendanceId()                        { return attendanceId; }
    public void setAttendanceId(int attendanceId)       { this.attendanceId = attendanceId; }

    public int getStudentId()                           { return studentId; }
    public void setStudentId(int studentId)             { this.studentId = studentId; }

    public String getStudentName()                      { return studentName; }
    public void setStudentName(String studentName)      { this.studentName = studentName; }

    public String getRollNumber()                       { return rollNumber; }
    public void setRollNumber(String rollNumber)        { this.rollNumber = rollNumber; }

    public int getCourseId()                            { return courseId; }
    public void setCourseId(int courseId)               { this.courseId = courseId; }

    public String getCourseName()                       { return courseName; }
    public void setCourseName(String courseName)        { this.courseName = courseName; }

    public LocalDate getDate()                          { return date; }
    public void setDate(LocalDate date)                 { this.date = date; }

    public String getStatus()                           { return status; }
    public void setStatus(String status)                { this.status = status; }

    public int getMarkedBy()                            { return markedBy; }
    public void setMarkedBy(int markedBy)               { this.markedBy = markedBy; }
}