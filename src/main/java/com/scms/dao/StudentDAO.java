package com.scms.dao;

import com.scms.model.Student;
import com.scms.util.DBUtil;
import com.scms.util.LogUtil;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static final Logger logger = LogUtil.getLogger(StudentDAO.class);

    // Get all students with department name (JOIN)
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = """
                SELECT s.*, d.dept_name
                FROM students s
                JOIN departments d ON s.department_id = d.dept_id
                ORDER BY s.name
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

            logger.debug("Fetched {} students from database", list.size());

        } catch (SQLException e) {
            logger.error("Error fetching all students", e);
            e.printStackTrace();
        }
        return list;
    }

    // Find student by ID
    public Student findById(int studentId) {
        String sql = """
                SELECT s.*, d.dept_name
                FROM students s
                JOIN departments d ON s.department_id = d.dept_id
                WHERE s.student_id = ?
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.debug("Found student with ID: {}", studentId);
                return mapRow(rs);
            }

            logger.warn("Student not found with ID: {}", studentId);

        } catch (SQLException e) {
            logger.error("Error finding student by ID: {}", studentId, e);
            e.printStackTrace();
        }
        return null;
    }

    // Find student by roll number
    public Student findByRollNumber(String rollNumber) {
        String sql = """
                SELECT s.*, d.dept_name
                FROM students s
                JOIN departments d ON s.department_id = d.dept_id
                WHERE s.roll_number = ?
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rollNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.debug("Found student with roll number: {}", rollNumber);
                return mapRow(rs);
            }

            logger.warn("Student not found with roll number: {}", rollNumber);

        } catch (SQLException e) {
            logger.error("Error finding student by roll number: {}", rollNumber, e);
            e.printStackTrace();
        }
        return null;
    }

    // Save new student
    public boolean save(Student s) {
        String sql = """
                INSERT INTO students
                (user_id, roll_number, name, email, phone, department_id, semester, batch_year)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getUserId());
            ps.setString(2, s.getRollNumber());
            ps.setString(3, s.getName());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getPhone());
            ps.setInt(6, s.getDepartmentId());
            ps.setInt(7, s.getSemester());
            ps.setInt(8, s.getBatchYear());

            boolean result = ps.executeUpdate() > 0;
            if (result) {
                logger.info("New student created: {} ({})", s.getName(), s.getRollNumber());
            }
            return result;

        } catch (SQLException e) {
            logger.error("Error saving student: {}", s.getRollNumber(), e);
            e.printStackTrace();
        }
        return false;
    }

    // Update student
    public boolean update(Student s) {
        String sql = """
                UPDATE students
                SET roll_number=?, name=?, email=?, phone=?,
                    department_id=?, semester=?, batch_year=?
                WHERE student_id=?
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getRollNumber());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPhone());
            ps.setInt(5, s.getDepartmentId());
            ps.setInt(6, s.getSemester());
            ps.setInt(7, s.getBatchYear());
            ps.setInt(8, s.getStudentId());

            boolean result = ps.executeUpdate() > 0;
            if (result) {
                logger.info("Student updated: {} (ID: {})", s.getName(), s.getStudentId());
            }
            return result;

        } catch (SQLException e) {
            logger.error("Error updating student with ID: {}", s.getStudentId(), e);
            e.printStackTrace();
        }
        return false;
    }

    // Delete student
    public boolean delete(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            boolean result = ps.executeUpdate() > 0;
            if (result) {
                logger.info("Student deleted with ID: {}", studentId);
            }
            return result;

        } catch (SQLException e) {
            logger.error("Error deleting student with ID: {}", studentId, e);
            e.printStackTrace();
        }
        return false;
    }

    // Map ResultSet row to Student object
    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setUserId(rs.getInt("user_id"));
        s.setRollNumber(rs.getString("roll_number"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setPhone(rs.getString("phone"));
        s.setDepartmentId(rs.getInt("department_id"));
        s.setSemester(rs.getInt("semester"));
        s.setBatchYear(rs.getInt("batch_year"));
        try {
            s.setDepartmentName(rs.getString("dept_name"));
        } catch (SQLException ignored) {}
        return s;
    }
}