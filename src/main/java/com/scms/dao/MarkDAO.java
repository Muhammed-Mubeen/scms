package com.scms.dao;

import com.scms.model.Mark;
import com.scms.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkDAO {

    // Get all marks for an exam
    public List<Mark> findByExam(int examId) {
        List<Mark> list = new ArrayList<>();
        String sql = """
                SELECT m.*, s.name AS student_name, s.roll_number,
                       e.exam_type, e.total_marks, c.course_name
                FROM marks m
                JOIN students s ON m.student_id = s.student_id
                JOIN exams    e ON m.exam_id    = e.exam_id
                JOIN courses  c ON e.course_id  = c.course_id
                WHERE m.exam_id = ?
                ORDER BY s.name
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get all marks for a student
    public List<Mark> findByStudent(int studentId) {
        List<Mark> list = new ArrayList<>();
        String sql = """
                SELECT m.*, s.name AS student_name, s.roll_number,
                       e.exam_type, e.total_marks, c.course_name
                FROM marks m
                JOIN students s ON m.student_id = s.student_id
                JOIN exams    e ON m.exam_id    = e.exam_id
                JOIN courses  c ON e.course_id  = c.course_id
                WHERE m.student_id = ?
                ORDER BY c.course_name, e.exam_type
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

    // Save or update a mark
    public boolean saveOrUpdate(Mark mark) {
        String sql = """
                INSERT INTO marks (exam_id, student_id, marks_obtained, grade)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    marks_obtained = VALUES(marks_obtained),
                    grade          = VALUES(grade)
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mark.getExamId());
            ps.setInt(2, mark.getStudentId());
            ps.setDouble(3, mark.getMarksObtained());
            ps.setString(4, mark.getGrade());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Mark mapRow(ResultSet rs) throws SQLException {
        Mark m = new Mark();
        m.setMarkId(rs.getInt("mark_id"));
        m.setExamId(rs.getInt("exam_id"));
        m.setStudentId(rs.getInt("student_id"));
        m.setMarksObtained(rs.getDouble("marks_obtained"));
        m.setGrade(rs.getString("grade"));
        try { m.setStudentName(rs.getString("student_name")); }
        catch (SQLException ignored) {}
        try { m.setRollNumber(rs.getString("roll_number")); }
        catch (SQLException ignored) {}
        try { m.setExamType(rs.getString("exam_type")); }
        catch (SQLException ignored) {}
        try { m.setTotalMarks(rs.getInt("total_marks")); }
        catch (SQLException ignored) {}
        try { m.setCourseName(rs.getString("course_name")); }
        catch (SQLException ignored) {}
        return m;
    }
}