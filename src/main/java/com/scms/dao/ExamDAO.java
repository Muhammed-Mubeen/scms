package com.scms.dao;

import com.scms.model.Exam;
import com.scms.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    public List<Exam> findAll() {
        List<Exam> list = new ArrayList<>();
        String sql = """
                SELECT e.*, c.course_name
                FROM exams e
                JOIN courses c ON e.course_id = c.course_id
                ORDER BY e.exam_date DESC
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Exam findById(int examId) {
        String sql = """
                SELECT e.*, c.course_name
                FROM exams e
                JOIN courses c ON e.course_id = c.course_id
                WHERE e.exam_id = ?
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Exam> findByCourse(int courseId) {
        List<Exam> list = new ArrayList<>();
        String sql = """
                SELECT e.*, c.course_name
                FROM exams e
                JOIN courses c ON e.course_id = c.course_id
                WHERE e.course_id = ?
                ORDER BY e.exam_date DESC
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean save(Exam exam) {
        String sql = """
                INSERT INTO exams (course_id, exam_type, total_marks, exam_date, academic_year)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, exam.getCourseId());
            ps.setString(2, exam.getExamType());
            ps.setInt(3, exam.getTotalMarks());
            ps.setDate(4, Date.valueOf(exam.getExamDate()));
            ps.setString(5, exam.getAcademicYear());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int examId) {
        String sql = "DELETE FROM exams WHERE exam_id = ?";

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Exam mapRow(ResultSet rs) throws SQLException {
        Exam e = new Exam();
        e.setExamId(rs.getInt("exam_id"));
        e.setCourseId(rs.getInt("course_id"));
        e.setExamType(rs.getString("exam_type"));
        e.setTotalMarks(rs.getInt("total_marks"));
        e.setExamDate(rs.getDate("exam_date").toLocalDate());
        e.setAcademicYear(rs.getString("academic_year"));
        try { e.setCourseName(rs.getString("course_name")); }
        catch (SQLException ignored) {}
        return e;
    }
}