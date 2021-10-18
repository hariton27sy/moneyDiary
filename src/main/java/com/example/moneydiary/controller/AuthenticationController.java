package com.example.moneydiary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final String refreshTokenCookie = "refreshToken";

    public AuthenticationController() {
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("logout")
    public ResponseEntity logout(@CookieValue(name = refreshTokenCookie) UUID refreshToken) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("refreshToken")
    public ResponseEntity refreshToken(@CookieValue(name = refreshTokenCookie) UUID refreshToken) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
