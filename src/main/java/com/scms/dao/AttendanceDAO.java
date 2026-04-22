package com.scms.dao;

import com.scms.model.Attendance;
import com.scms.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AttendanceDAO {

    // Bulk insert attendance for a full class on a date
    public boolean bulkInsert(LinkedList<Attendance> records) {
        String sql = """
                INSERT INTO attendance (student_id, course_id, date, status, marked_by)
                VALUES (?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE status = VALUES(status)
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (Attendance a : records) {
                ps.setInt(1, a.getStudentId());
                ps.setInt(2, a.getCourseId());
                ps.setDate(3, Date.valueOf(a.getDate()));
                ps.setString(4, a.getStatus());
                ps.setInt(5, a.getMarkedBy());
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get attendance by course and date
    public List<Attendance> findByCourseAndDate(int courseId, LocalDate date) {
        List<Attendance> list = new ArrayList<>();
        String sql = """
                SELECT a.*, s.name AS student_name, s.roll_number
                FROM attendance a
                JOIN students s ON a.student_id = s.student_id
                WHERE a.course_id = ? AND a.date = ?
                ORDER BY s.name
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get attendance by student
    public List<Attendance> findByStudent(int studentId) {
        List<Attendance> list = new ArrayList<>();
        String sql = """
                SELECT a.*, s.name AS student_name, s.roll_number,
                       c.course_name
                FROM attendance a
                JOIN students s ON a.student_id = s.student_id
                JOIN courses  c ON a.course_id  = c.course_id
                WHERE a.student_id = ?
                ORDER BY a.date DESC
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Count present days for a student in a course
    public int countPresent(int studentId, int courseId) {
        String sql = """
                SELECT COUNT(*) FROM attendance
                WHERE student_id = ? AND course_id = ? AND status = 'P'
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Count total days for a student in a course
    public int countTotal(int studentId, int courseId) {
        String sql = """
                SELECT COUNT(*) FROM attendance
                WHERE student_id = ? AND course_id = ?
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Attendance mapRow(ResultSet rs) throws SQLException {
        Attendance a = new Attendance();
        a.setAttendanceId(rs.getInt("attendance_id"));
        a.setStudentId(rs.getInt("student_id"));
        a.setCourseId(rs.getInt("course_id"));
        a.setDate(rs.getDate("date").toLocalDate());
        a.setStatus(rs.getString("status"));
        a.setMarkedBy(rs.getInt("marked_by"));
        try { a.setStudentName(rs.getString("student_name")); }
        catch (SQLException ignored) {}
        try { a.setRollNumber(rs.getString("roll_number")); }
        catch (SQLException ignored) {}
        try { a.setCourseName(rs.getString("course_name")); }
        catch (SQLException ignored) {}
        return a;
    }
}