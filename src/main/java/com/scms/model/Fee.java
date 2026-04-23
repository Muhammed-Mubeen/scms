package com.scms.model;

import java.time.LocalDate;

public class Fee {
    private int feeId;
    private int studentId;
    private String studentName;   // for display
    private String rollNumber;    // for display
    private double amount;
    private double paidAmount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private String status;        // pending, partial, paid
    private int semester;
    private String academicYear;

    public Fee() {}

    public int getFeeId()                           { return feeId; }
    public void setFeeId(int feeId)                 { this.feeId = feeId; }

    public int getStudentId()                       { return studentId; }
    public void setStudentId(int studentId)         { this.studentId = studentId; }

    public String getStudentName()                  { return studentName; }
    public void setStudentName(String studentName)  { this.studentName = studentName; }

    public String getRollNumber()                   { return rollNumber; }
    public void setRollNumber(String rollNumber)    { this.rollNumber = rollNumber; }

    public double getAmount()                       { return amount; }
    public void setAmount(double amount)            { this.amount = amount; }

    public double getPaidAmount()                   { return paidAmount; }
    public void setPaidAmount(double paidAmount)    { this.paidAmount = paidAmount; }

    public LocalDate getDueDate()                   { return dueDate; }
    public void setDueDate(LocalDate dueDate)       { this.dueDate = dueDate; }

    public LocalDate getPaidDate()                  { return paidDate; }
    public void setPaidDate(LocalDate paidDate)     { this.paidDate = paidDate; }

    public String getStatus()                       { return status; }
    public void setStatus(String status)            { this.status = status; }

    public int getSemester()                        { return semester; }
    public void setSemester(int semester)           { this.semester = semester; }

    public String getAcademicYear()                 { return academicYear; }
    public void setAcademicYear(String academicYear){ this.academicYear = academicYear; }

    // Helper — outstanding due
    public double getDue() {
        return amount - paidAmount;
    }
}