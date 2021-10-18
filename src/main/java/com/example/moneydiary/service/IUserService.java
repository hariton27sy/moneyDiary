package com.example.moneydiary.service;

import com.example.moneydiary.model.UserShortSession;

import java.util.Optional;

public interface IUserService {
    Optional<UserShortSession> authenticateByLoginAndPassword(String username, String password);
}
