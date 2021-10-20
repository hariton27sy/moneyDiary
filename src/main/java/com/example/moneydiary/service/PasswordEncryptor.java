package com.example.moneydiary.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptor {
    private final BCryptPasswordEncoder encoder;

    public PasswordEncryptor() {
        encoder = new BCryptPasswordEncoder(10);
    }

    public String encryptPassword(String password){
        return encoder.encode(password);
    }

    public boolean checkPassword(String rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword, encryptedPassword);
    }
}
