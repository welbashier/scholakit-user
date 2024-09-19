package com.gwais.sk_users.dto;

public class AuthenticationRequest {

    private String username;
    private String password;

    // Default constructor
    public AuthenticationRequest() {
    }

    // Constructor with parameters
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}