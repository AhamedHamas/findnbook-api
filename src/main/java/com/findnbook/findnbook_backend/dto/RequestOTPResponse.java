package com.findnbook.findnbook_backend.dto;

public class RequestOTPResponse {
    private String email;
    private String message;

    public RequestOTPResponse(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
