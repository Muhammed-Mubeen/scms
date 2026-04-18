package com.scms.model;

public class Department {
    private int deptId;
    private String deptName;
    private int hodFacultyId;

    public Department() {}

    public Department(String deptName) {
        this.deptName = deptName;
    }

    public int getDeptId()                      { return deptId; }
    public void setDeptId(int deptId)           { this.deptId = deptId; }

    public String getDeptName()                 { return deptName; }
    public void setDeptName(String deptName)    { this.deptName = deptName; }

    public int getHodFacultyId()                { return hodFacultyId; }
    public void setHodFacultyId(int id)         { this.hodFacultyId = id; }

    @Override
    public String toString() { return deptName; }
}