package com.findnbook.findnbook_backend.dto;

// Response DTO for Verify OTP
public class VerifyOTPResponse {
    private String token;
    private UserDTO user;

    // constructor, getters, setters
    public VerifyOTPResponse(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }

    // Getters & Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
