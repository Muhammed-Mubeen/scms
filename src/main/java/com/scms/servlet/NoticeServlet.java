package com.scms.servlet;

import com.scms.model.Notice;
import com.scms.model.User;
import com.scms.service.NoticeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/notices")
public class NoticeServlet extends HttpServlet {

    private final NoticeService noticeService = new NoticeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "add" -> {
                req.setAttribute("departments",
                        noticeService.getAllDepartments());
                req.getRequestDispatcher("/WEB-INF/views/admin/add-notice.jsp")
                        .forward(req, resp);
            }

            case "delete" -> {
                int id = Integer.parseInt(req.getParameter("id"));
                noticeService.deleteNotice(id);
                req.getSession().setAttribute("successMsg",
                        "Notice deleted successfully!");
                resp.sendRedirect(req.getContextPath() + "/admin/notices");
            }

            default -> {
                req.setAttribute("notices",
                        noticeService.getAllNotices());
                req.getRequestDispatcher("/WEB-INF/views/admin/notice-board.jsp")
                        .forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session  = req.getSession();
        User loggedUser      = (User) session.getAttribute("loggedUser");

        try {
            Notice notice = new Notice();
            notice.setTitle(req.getParameter("title"));
            notice.setContent(req.getParameter("content"));
            notice.setPostedBy(loggedUser.getUserId());

            String deptParam = req.getParameter("departmentId");
            if (deptParam != null && !deptParam.isBlank()) {
                notice.setDepartmentId(Integer.parseInt(deptParam));
            }

            noticeService.postNotice(notice);
            req.getSession().setAttribute("successMsg",
                    "Notice posted successfully!");

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("errorMsg", e.getMessage());
        } catch (Exception e) {
            req.getSession().setAttribute("errorMsg",
                    "Error: " + e.getMessage());
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/notices");
    }
}