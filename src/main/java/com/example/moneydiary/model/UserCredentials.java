package com.example.moneydiary.model;

import javax.validation.constraints.NotNull;

public class UserCredentials {
    private final String accessToken;
    private final String refreshToken;

    public UserCredentials(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @NotNull
    public String getAccessToken() {
        return accessToken;
    }

    @NotNull
    public String getRefreshToken() {
        return refreshToken;
    }
}
