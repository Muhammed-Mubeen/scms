package com.scms.service;

import com.scms.dao.DepartmentDAO;
import com.scms.model.Department;

import java.util.List;

public class DepartmentService {

    private final DepartmentDAO departmentDAO = new DepartmentDAO();

    public List<Department> getAllDepartments() {
        return departmentDAO.findAll();
    }

    public Department getDepartmentById(int id) {
        return departmentDAO.findById(id);
    }

    public boolean addDepartment(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }
        Department dept = new Department(name.trim());
        return departmentDAO.save(dept);
    }
}