package com.scms.dao;

import com.scms.model.Notice;
import com.scms.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class NoticeDAO {

    // Get all notices sorted by date (TreeMap)
    public List<Notice> findAll() {
        TreeMap<LocalDateTime, Notice> treeMap = new TreeMap<>(
                java.util.Collections.reverseOrder()
        );
        String sql = """
                SELECT n.*, u.username AS posted_by_name,
                       d.dept_name
                FROM notices n
                JOIN users u ON n.posted_by = u.user_id
                LEFT JOIN departments d ON n.department_id = d.dept_id
                ORDER BY n.created_at DESC
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Notice notice = mapRow(rs);
                treeMap.put(notice.getCreatedAt(), notice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(treeMap.values());
    }

    // Get notices by department (NULL = all depts)
    public List<Notice> findByDepartment(int deptId) {
        List<Notice> list = new ArrayList<>();
        String sql = """
                SELECT n.*, u.username AS posted_by_name,
                       d.dept_name
                FROM notices n
                JOIN users u ON n.posted_by = u.user_id
                LEFT JOIN departments d ON n.department_id = d.dept_id
                WHERE n.department_id = ? OR n.department_id IS NULL
                ORDER BY n.created_at DESC
                """;

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

    // Save notice
    public boolean save(Notice notice) {
        String sql = """
                INSERT INTO notices (title, content, posted_by, department_id)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, notice.getTitle());
            ps.setString(2, notice.getContent());
            ps.setInt(3, notice.getPostedBy());
            if (notice.getDepartmentId() == 0) {
                ps.setNull(4, Types.INTEGER); // NULL = broadcast
            } else {
                ps.setInt(4, notice.getDepartmentId());
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete notice
    public boolean delete(int noticeId) {
        String sql = "DELETE FROM notices WHERE notice_id = ?";

        try (Connection con = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, noticeId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Notice mapRow(ResultSet rs) throws SQLException {
        Notice n = new Notice();
        n.setNoticeId(rs.getInt("notice_id"));
        n.setTitle(rs.getString("title"));
        n.setContent(rs.getString("content"));
        n.setPostedBy(rs.getInt("posted_by"));
        n.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        try { n.setPostedByName(rs.getString("posted_by_name")); }
        catch (SQLException ignored) {}
        try { n.setDepartmentName(rs.getString("dept_name")); }
        catch (SQLException ignored) {}
        int deptId = rs.getInt("department_id");
        if (!rs.wasNull()) n.setDepartmentId(deptId);
        return n;
    }
}