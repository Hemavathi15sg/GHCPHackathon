package com.example.dto;

public class RegisterResponse {

    private final String userId;

    public RegisterResponse(String userId) {
        this.userId = userId;
    }

    public String getUserId() { return userId; }
}
