package com.healthsphere.dto;

public class LoginResponse {
    private String token;
    private String role;
    private String status;
    private String message;  // <-- new field
    public LoginResponse(String token, String role, String status,String message) {
        this.token = token;
        this.role = role;
        this.status = status;
        this.message = message;
    }

    public LoginResponse() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // getters
    public String getToken() { return token; }
    public String getRole() { return role; }
    public String getStatus() { return status; }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
