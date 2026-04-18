package com.scms.service;

import com.scms.dao.StudentDAO;
import com.scms.dao.UserDAO;
import com.scms.model.Student;
import com.scms.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO = new StudentDAO();
    private final UserDAO userDAO       = new UserDAO();

    // Get all students
    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    // Get student by ID
    public Student getStudentById(int id) {
        return studentDAO.findById(id);
    }

    // Add new student — also creates a user account
    public boolean addStudent(Student student, String plainPassword) {
        // 1. Check duplicate roll number
        if (studentDAO.findByRollNumber(student.getRollNumber()) != null) {
            throw new IllegalArgumentException("Roll number already exists: " + student.getRollNumber());
        }

        // 2. Create user account with hashed password
        String hash = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
        User user = new User(student.getRollNumber(), hash, "student");
        boolean userCreated = userDAO.save(user);

        if (!userCreated) {
            throw new RuntimeException("Failed to create user account.");
        }

        // 3. Get the generated user_id
        User savedUser = userDAO.findByUsername(student.getRollNumber());
        student.setUserId(savedUser.getUserId());

        // 4. Save student record
        return studentDAO.save(student);
    }

    // Update existing student
    public boolean updateStudent(Student student) {
        // Check duplicate roll number for OTHER students
        Student existing = studentDAO.findByRollNumber(student.getRollNumber());
        if (existing != null && existing.getStudentId() != student.getStudentId()) {
            throw new IllegalArgumentException("Roll number already used by another student.");
        }
        return studentDAO.update(student);
    }

    // Delete student
    public boolean deleteStudent(int studentId) {
        return studentDAO.delete(studentId);
    }
}