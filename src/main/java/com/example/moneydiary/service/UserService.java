package com.example.moneydiary.service;


import com.example.moneydiary.model.UserShortSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Override
    public Optional<UserShortSession> authenticateByLoginAndPassword(String username, String password) {
        return Optional.empty();
    }
}
