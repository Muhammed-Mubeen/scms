package com.scms.model;

public class Student {
    private int studentId;
    private int userId;
    private String rollNumber;
    private String name;
    private String email;
    private String phone;
    private int departmentId;
    private String departmentName; // for display only
    private int semester;
    private int batchYear;

    public Student() {}

    // Getters & Setters
    public int getStudentId()                       { return studentId; }
    public void setStudentId(int studentId)         { this.studentId = studentId; }

    public int getUserId()                          { return userId; }
    public void setUserId(int userId)               { this.userId = userId; }

    public String getRollNumber()                   { return rollNumber; }
    public void setRollNumber(String rollNumber)    { this.rollNumber = rollNumber; }

    public String getName()                         { return name; }
    public void setName(String name)                { this.name = name; }

    public String getEmail()                        { return email; }
    public void setEmail(String email)              { this.email = email; }

    public String getPhone()                        { return phone; }
    public void setPhone(String phone)              { this.phone = phone; }

    public int getDepartmentId()                    { return departmentId; }
    public void setDepartmentId(int departmentId)   { this.departmentId = departmentId; }

    public String getDepartmentName()               { return departmentName; }
    public void setDepartmentName(String name)      { this.departmentName = name; }

    public int getSemester()                        { return semester; }
    public void setSemester(int semester)           { this.semester = semester; }

    public int getBatchYear()                       { return batchYear; }
    public void setBatchYear(int batchYear)         { this.batchYear = batchYear; }

    @Override
    public String toString() {
        return "Student{id=" + studentId + ", name=" + name + ", roll=" + rollNumber + "}";
    }
}