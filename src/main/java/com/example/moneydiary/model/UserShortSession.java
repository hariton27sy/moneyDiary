package com.example.moneydiary.model;

import java.util.Date;

public class UserShortSession {
    private final Long userId;
    private final String username;
    private final Date expiresIn;
    private final String token;

    public UserShortSession(Long userId, String username, Date expiresIn, String token){
        this.userId = userId;
        this.username = username;
        this.expiresIn = expiresIn;
        this.token = token;
    }

    public boolean isExpired() {
        return new Date().after(expiresIn);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
