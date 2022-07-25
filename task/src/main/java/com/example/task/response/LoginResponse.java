package com.example.task.response;

import java.util.List;
import java.util.UUID;

public class LoginResponse {
    private String tokenType;
    private String jwt;
    private UUID id;
    private String email;
    private String username;
    private List<String> roles;

    public LoginResponse() {
    }

    public LoginResponse(String tokenType, String jwt, UUID id, String email, String username, List<String> roles) {
        this.tokenType = tokenType;
        this.jwt = jwt;
        this.id = id;
        this.email = email;
        this.username = username;
        this.roles = roles;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
