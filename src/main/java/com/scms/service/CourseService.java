package com.scms.service;

import com.scms.dao.CourseDAO;
import com.scms.dao.DepartmentDAO;
import com.scms.model.Course;
import com.scms.model.Department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseService {

    private final CourseDAO      courseDAO      = new CourseDAO();
    private final DepartmentDAO  departmentDAO  = new DepartmentDAO();

    // Get all courses
    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    // Get course by ID
    public Course getCourseById(int id) {
        return courseDAO.findById(id);
    }

    // Get courses grouped by department using HashMap
    public Map<String, List<Course>> getCoursesByDepartment() {
        List<Department> departments = departmentDAO.findAll();
        Map<String, List<Course>> map = new HashMap<>();

        for (Department dept : departments) {
            List<Course> courses = courseDAO.findByDepartment(dept.getDeptId());
            if (!courses.isEmpty()) {
                map.put(dept.getDeptName(), courses);
            }
        }
        return map;
    }

    // Add new course
    public boolean addCourse(Course course) {
        // Validate credits range
        if (course.getCredits() < 1 || course.getCredits() > 6) {
            throw new IllegalArgumentException("Credits must be between 1 and 6.");
        }
        // Validate semester range
        if (course.getSemester() < 1 || course.getSemester() > 8) {
            throw new IllegalArgumentException("Semester must be between 1 and 8.");
        }
        return courseDAO.save(course);
    }

    // Update course
    public boolean updateCourse(Course course) {
        if (course.getCredits() < 1 || course.getCredits() > 6) {
            throw new IllegalArgumentException("Credits must be between 1 and 6.");
        }
        return courseDAO.update(course);
    }

    // Delete course
    public boolean deleteCourse(int courseId) {
        return courseDAO.delete(courseId);
    }

    // Get all departments (for dropdowns)
    public List<Department> getAllDepartments() {
        return departmentDAO.findAll();
    }
}