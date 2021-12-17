package com.example.moneydiary.model;

public class UserData {
    private String username;
    private String userImage;

    public UserData(String username, String userImage) {

        this.username = username;
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public String getUserImage() {
        return userImage;
    }
}
