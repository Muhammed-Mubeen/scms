package com.scms.service;

import com.scms.dao.DepartmentDAO;
import com.scms.dao.NoticeDAO;
import com.scms.model.Department;
import com.scms.model.Notice;

import java.util.List;

public class NoticeService {

    private final NoticeDAO     noticeDAO     = new NoticeDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();

    // Get all notices sorted by date (TreeMap in DAO)
    public List<Notice> getAllNotices() {
        return noticeDAO.findAll();
    }

    // Get notices for a specific department
    // (includes broadcast notices where dept is NULL)
    public List<Notice> getNoticesForDepartment(int deptId) {
        return noticeDAO.findByDepartment(deptId);
    }

    // Post a new notice
    public boolean postNotice(Notice notice) {
        if (notice.getTitle() == null || notice.getTitle().isBlank()) {
            throw new IllegalArgumentException("Notice title cannot be empty.");
        }
        if (notice.getContent() == null || notice.getContent().isBlank()) {
            throw new IllegalArgumentException("Notice content cannot be empty.");
        }
        return noticeDAO.save(notice);
    }

    // Delete a notice
    public boolean deleteNotice(int noticeId) {
        return noticeDAO.delete(noticeId);
    }

    // Get all departments (for dropdown)
    public List<Department> getAllDepartments() {
        return departmentDAO.findAll();
    }
}