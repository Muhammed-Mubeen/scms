package com.scms.dao;

import com.scms.model.Course;
import com.scms.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Get all courses with dept + faculty name (JOIN)
    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        String sql = """
                SELECT c.*, d.dept_name,
                       CONCAT(f.name) AS faculty_name
                FROM courses c
                JOIN departments d ON c.department_id = d.dept_id
                LEFT JOIN faculty f ON c.faculty_id = f.faculty_id
                ORDER BY c.course_code
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Find course by ID
    public Course findById(int courseId) {
        String sql = """
                SELECT c.*, d.dept_name,
                       f.name AS faculty_name
                FROM courses c
                JOIN departments d ON c.department_id = d.dept_id
                LEFT JOIN faculty f ON c.faculty_id = f.faculty_id
                WHERE c.course_id = ?
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Find courses by department
    public List<Course> findByDepartment(int deptId) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE department_id = ? ORDER BY semester";

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, deptId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Save new course
    public boolean save(Course c) {
        String sql = """
                INSERT INTO courses
                (course_code, course_name, department_id, faculty_id, credits, semester)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCourseCode());
            ps.setString(2, c.getCourseName());
            ps.setInt(3, c.getDepartmentId());
            if (c.getFacultyId() > 0) {
                ps.setInt(4, c.getFacultyId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            //ps.setInt(4, c.getFacultyId());
            ps.setInt(5, c.getCredits());
            ps.setInt(6, c.getSemester());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update course
    public boolean update(Course c) {
        String sql = """
                UPDATE courses
                SET course_code=?, course_name=?, department_id=?,
                    faculty_id=?, credits=?, semester=?
                WHERE course_id=?
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCourseCode());
            ps.setString(2, c.getCourseName());
            ps.setInt(3, c.getDepartmentId());
            if (c.getFacultyId() > 0) {
                ps.setInt(4, c.getFacultyId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
           // ps.setInt(4, c.getFacultyId());
            ps.setInt(5, c.getCredits());
            ps.setInt(6, c.getSemester());
            ps.setInt(7, c.getCourseId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete course
    public boolean delete(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Map ResultSet to Course
    private Course mapRow(ResultSet rs) throws SQLException {
        Course c = new Course();
        c.setCourseId(rs.getInt("course_id"));
        c.setCourseCode(rs.getString("course_code"));
        c.setCourseName(rs.getString("course_name"));
        c.setDepartmentId(rs.getInt("department_id"));
        c.setFacultyId(rs.getInt("faculty_id"));
        c.setCredits(rs.getInt("credits"));
        c.setSemester(rs.getInt("semester"));
        try { c.setDepartmentName(rs.getString("dept_name")); }
        catch (SQLException ignored) {}
        try { c.setFacultyName(rs.getString("faculty_name")); }
        catch (SQLException ignored) {}
        return c;
    }
}