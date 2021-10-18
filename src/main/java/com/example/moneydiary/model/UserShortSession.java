package com.example.moneydiary.model;

import java.util.Calendar;
import java.util.Date;

public class UserShortSession {
    private Long userId;
    private String username;
    private Date expiresIn;
    private Calendar calendar;

    public UserShortSession(){
        calendar = Calendar.getInstance();
    }

    public boolean isExpired() {
        return false;
    }
}
