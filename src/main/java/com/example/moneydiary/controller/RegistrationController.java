package com.example.moneydiary.controller;

import com.example.moneydiary.dto.UserDto;
import com.example.moneydiary.model.RequestContext;
import com.example.moneydiary.model.UserData;
import com.example.moneydiary.repository.UserDtoRepository;
import com.example.moneydiary.service.PasswordEncryptor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RegistrationController {
    private UserDtoRepository userDtoRepository;
    private PasswordEncryptor passwordEncryptor;
    private ObjectFactory<RequestContext> requestContextObjectFactory;

    public RegistrationController(UserDtoRepository userDtoRepository, PasswordEncryptor passwordEncryptor, ObjectFactory<RequestContext> requestContextObjectFactory) {

        this.userDtoRepository = userDtoRepository;
        this.passwordEncryptor = passwordEncryptor;
        this.requestContextObjectFactory = requestContextObjectFactory;
    }

    @GetMapping("/register")
    public ResponseEntity register(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam(required = false) String profileImgLink) {
        if (userDtoRepository.existsByEmail(email))
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/register.html")).build();

        if (profileImgLink == null)
            profileImgLink = "https://html5css.ru/howto/img_avatar.png";
        userDtoRepository.save(new UserDto(name, passwordEncryptor.encryptPassword(password), email, profileImgLink));
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/login.html?redirect=%2F")).build();
    }

    @GetMapping("api/userInfo")
    public ResponseEntity getUserInfo() {
        var context = requestContextObjectFactory.getObject();
        var userSession = context.getUser();
        var user = userDtoRepository.findById(userSession.getUserId()).get();
        return ResponseEntity.ok(new UserData(user.getUsername(), user.getImgLink()));
    }
}
