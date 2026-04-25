package com.scms.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/faculty/*", "/student/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("loggedUser") != null);

        if (!loggedIn) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Role-based access
        String role = (String) session.getAttribute("role");
        String uri  = req.getRequestURI();

        if (uri.contains("/admin/")) {
            // Exception for attendance (allow faculty)
            if (uri.contains("/admin/attendance") && "faculty".equals(role)) {
                // allow
            }
            // Exception for marks (allow faculty)
            else if (uri.contains("/admin/marks") && "faculty".equals(role)) {
                // allow
            }
            // Exception for notices (allow faculty and student)
            else if (uri.contains("/admin/notices") && ("faculty".equals(role) || "student".equals(role))) {
                // allow
            }
            else if (!"admin".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        }
        if (uri.contains("/faculty/") && !"faculty".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (uri.contains("/student/") && !"student".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}