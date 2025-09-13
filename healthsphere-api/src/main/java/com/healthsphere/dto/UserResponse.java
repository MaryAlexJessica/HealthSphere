package com.healthsphere.dto;

public class UserResponse {
    private Long id;
    private String username;
    private String role;
    private String status;

    public UserResponse(Long id, String username, String role, String status) {
        this.id = id; this.username = username; this.role = role; this.status = status;
    }
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
}
