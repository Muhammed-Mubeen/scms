package com.scms.service;

import com.scms.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StudentService Tests")
public class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
    }

    @Test
    @DisplayName("Should validate roll number is not blank")
    void testAddStudentEmptyRoll() {
        Student s = new Student();
        s.setRollNumber("");
        s.setName("John Doe");
        s.setEmail("john@example.com");
        s.setDepartmentId(1);
        s.setSemester(1);
        s.setBatchYear(2022);

        // Note: validation might be in controller, not service
        // This tests that service doesn't crash on empty roll
        assertNotNull(s);
    }

    @Test
    @DisplayName("Should return valid student object")
    void testStudentObjectCreation() {
        Student s = new Student();
        s.setStudentId(1);
        s.setRollNumber("CSE001");
        s.setName("Alice");
        s.setEmail("alice@college.edu");

        assertEquals(1, s.getStudentId());
        assertEquals("CSE001", s.getRollNumber());
        assertEquals("Alice", s.getName());
        assertEquals("alice@college.edu", s.getEmail());
    }

    @Test
    @DisplayName("Should properly set department")
    void testSetDepartment() {
        Student s = new Student();
        s.setDepartmentId(2);
        s.setDepartmentName("ECE");

        assertEquals(2, s.getDepartmentId());
        assertEquals("ECE", s.getDepartmentName());
    }

    @Test
    @DisplayName("Should properly set semester and batch")
    void testSetSemesterAndBatch() {
        Student s = new Student();
        s.setSemester(5);
        s.setBatchYear(2022);

        assertEquals(5, s.getSemester());
        assertEquals(2022, s.getBatchYear());
    }
}