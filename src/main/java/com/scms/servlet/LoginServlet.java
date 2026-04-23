package com.scms.servlet;

import com.scms.model.User;
import com.scms.service.UserService;
import com.scms.util.LogUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private static final Logger logger = LogUtil.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        logger.info("Login attempt for user: {}", username);

        User user = userService.login(username, password);

        if (user == null) {
            logger.warn("Failed login attempt for user: {}", username);
            req.setAttribute("error", "Invalid username or password.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        logger.info("Successful login for user: {} (role: {})", username, user.getRole());

        HttpSession session = req.getSession();
        session.setAttribute("loggedUser", user);
        session.setAttribute("role", user.getRole());

        switch (user.getRole()) {
            case "admin"   -> resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            case "faculty" -> resp.sendRedirect(req.getContextPath() + "/faculty/dashboard");
            case "student" -> resp.sendRedirect(req.getContextPath() + "/student/dashboard");
            default        -> resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}