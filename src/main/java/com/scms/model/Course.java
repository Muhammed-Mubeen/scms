package com.scms.model;

public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private int departmentId;
    private String departmentName; // for display only
    private int facultyId;
    private String facultyName;    // for display only
    private int credits;
    private int semester;

    public Course() {}

    // Getters & Setters
    public int getCourseId()                        { return courseId; }
    public void setCourseId(int courseId)           { this.courseId = courseId; }

    public String getCourseCode()                   { return courseCode; }
    public void setCourseCode(String courseCode)    { this.courseCode = courseCode; }

    public String getCourseName()                   { return courseName; }
    public void setCourseName(String courseName)    { this.courseName = courseName; }

    public int getDepartmentId()                    { return departmentId; }
    public void setDepartmentId(int departmentId)   { this.departmentId = departmentId; }

    public String getDepartmentName()               { return departmentName; }
    public void setDepartmentName(String name)      { this.departmentName = name; }

    public int getFacultyId()                       { return facultyId; }
    public void setFacultyId(int facultyId)         { this.facultyId = facultyId; }

    public String getFacultyName()                  { return facultyName; }
    public void setFacultyName(String name)         { this.facultyName = name; }

    public int getCredits()                         { return credits; }
    public void setCredits(int credits)             { this.credits = credits; }

    public int getSemester()                        { return semester; }
    public void setSemester(int semester)           { this.semester = semester; }

    @Override
    public String toString() {
        return "Course{id=" + courseId + ", code=" + courseCode + ", name=" + courseName + "}";
    }
}