package com.scms.servlet;

import com.scms.model.User;
import com.scms.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    // GET — show login page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    // POST — process login
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // TEMP DEBUG — remove later
        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("Username entered: " + username);
        System.out.println("Password entered: " + password);

        User user = userService.login(username, password);

        System.out.println("User from DB: " + user);
        // END DEBUG

        if (user == null) {
            req.setAttribute("error", "Invalid username or password.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        // Create session and store user info
        HttpSession session = req.getSession();
        session.setAttribute("loggedUser", user);
        session.setAttribute("role", user.getRole());

        // Redirect based on role
        switch (user.getRole()) {
            case "admin"   -> resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            case "faculty" -> resp.sendRedirect(req.getContextPath() + "/faculty/dashboard");
            case "student" -> resp.sendRedirect(req.getContextPath() + "/student/dashboard");
            default        -> resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}