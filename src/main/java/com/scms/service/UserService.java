package com.scms.service;

import com.scms.dao.UserDAO;
import com.scms.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private final UserDAO userDAO = new UserDAO();


    // Validate login — returns User if valid, null if not
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);


        if (user == null) return null;

        // BCrypt check
        if (!BCrypt.checkpw(password, user.getPasswordHash())) return null;

        return user;
    }

    // Register a new user with hashed password
    public boolean register(String username, String plainPassword, String role) {
        String hash = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
        User user = new User(username, hash, role);
        return userDAO.save(user);
    }
}