package com.scms.dao;

import com.scms.model.Fee;
import com.scms.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeeDAO {

    // Get all fees with student info
    public List<Fee> findAll() {
        List<Fee> list = new ArrayList<>();
        String sql = """
                SELECT f.*, s.name AS student_name, s.roll_number
                FROM fees f
                JOIN students s ON f.student_id = s.student_id
                ORDER BY f.due_date DESC
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

    // Get fees by student
    public List<Fee> findByStudent(int studentId) {
        List<Fee> list = new ArrayList<>();
        String sql = """
                SELECT f.*, s.name AS student_name, s.roll_number
                FROM fees f
                JOIN students s ON f.student_id = s.student_id
                WHERE f.student_id = ?
                ORDER BY f.due_date DESC
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

    // Find fee by ID
    public Fee findById(int feeId) {
        String sql = """
                SELECT f.*, s.name AS student_name, s.roll_number
                FROM fees f
                JOIN students s ON f.student_id = s.student_id
                WHERE f.fee_id = ?
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, feeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save new fee record
    public boolean save(Fee fee) {
        String sql = """
                INSERT INTO fees
                (student_id, amount, paid_amount, due_date, status, semester, academic_year)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, fee.getStudentId());
            ps.setDouble(2, fee.getAmount());
            ps.setDouble(3, fee.getPaidAmount());
            ps.setDate(4, Date.valueOf(fee.getDueDate()));
            ps.setString(5, fee.getStatus());
            ps.setInt(6, fee.getSemester());
            ps.setString(7, fee.getAcademicYear());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Record a payment — update paid_amount, status, paid_date
    public boolean recordPayment(int feeId, double paymentAmount) {
        // First get current fee
        Fee fee = findById(feeId);
        if (fee == null) return false;

        double newPaid = fee.getPaidAmount() + paymentAmount;
        String newStatus;

        if (newPaid >= fee.getAmount()) {
            newPaid   = fee.getAmount(); // cap at total
            newStatus = "paid";
        } else if (newPaid > 0) {
            newStatus = "partial";
        } else {
            newStatus = "pending";
        }

        String sql = """
                UPDATE fees
                SET paid_amount = ?, status = ?, paid_date = ?
                WHERE fee_id = ?
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, newPaid);
            ps.setString(2, newStatus);
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setInt(4, feeId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get pending/partial fees
    public List<Fee> findPending() {
        List<Fee> list = new ArrayList<>();
        String sql = """
                SELECT f.*, s.name AS student_name, s.roll_number
                FROM fees f
                JOIN students s ON f.student_id = s.student_id
                WHERE f.status IN ('pending', 'partial')
                ORDER BY f.due_date ASC
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

    private Fee mapRow(ResultSet rs) throws SQLException {
        Fee f = new Fee();
        f.setFeeId(rs.getInt("fee_id"));
        f.setStudentId(rs.getInt("student_id"));
        f.setAmount(rs.getDouble("amount"));
        f.setPaidAmount(rs.getDouble("paid_amount"));
        f.setDueDate(rs.getDate("due_date").toLocalDate());
        f.setStatus(rs.getString("status"));
        f.setSemester(rs.getInt("semester"));
        f.setAcademicYear(rs.getString("academic_year"));
        try { f.setStudentName(rs.getString("student_name")); }
        catch (SQLException ignored) {}
        try { f.setRollNumber(rs.getString("roll_number")); }
        catch (SQLException ignored) {}
        Date paidDate = rs.getDate("paid_date");
        if (paidDate != null) f.setPaidDate(paidDate.toLocalDate());
        return f;
    }
}