package com.scms.model;

import java.time.LocalDateTime;

public class User {

    private int userId;
    private String username;
    private String passwordHash;
    private String role; // "admin", "faculty", "student"
    private boolean isActive;
    private LocalDateTime createdAt;

    // ── Constructors ──────────────────────────────────────────
    public User() {}

    public User(String username, String passwordHash, String role) {
        this.username     = username;
        this.passwordHash = passwordHash;
        this.role         = role;
        this.isActive     = true;
    }

    // ── Getters & Setters ─────────────────────────────────────
    public int getUserId()                     { return userId; }
    public void setUserId(int userId)          { this.userId = userId; }

    public String getUsername()                { return username; }
    public void setUsername(String username)   { this.username = username; }

    public String getPasswordHash()            { return passwordHash; }
    public void setPasswordHash(String h)      { this.passwordHash = h; }

    public String getRole()                    { return role; }
    public void setRole(String role)           { this.role = role; }

    public boolean isActive()                  { return isActive; }
    public void setActive(boolean active)      { this.isActive = active; }

    public LocalDateTime getCreatedAt()        { return createdAt; }
    public void setCreatedAt(LocalDateTime t)  { this.createdAt = t; }

    @Override
    public String toString() {
        return "User{id=" + userId + ", username=" + username + ", role=" + role + "}";
    }
}
