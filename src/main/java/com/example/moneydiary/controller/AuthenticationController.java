package com.example.moneydiary.controller;

import com.example.moneydiary.Constants;
import com.example.moneydiary.dto.UserDto;
import com.example.moneydiary.model.RequestContext;
import com.example.moneydiary.model.UserCredentials;
import com.example.moneydiary.model.UserShortSession;
import com.example.moneydiary.repository.UserDtoRepository;
import com.example.moneydiary.service.IUserSessionService;
import com.example.moneydiary.service.PasswordEncryptor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final String refreshTokenCookie = "refreshToken";
    private final ObjectFactory<RequestContext> requestContextProvider;
    private final UserDtoRepository userDtoRepository;
    private final PasswordEncryptor passwordEncryptor;
    private final IUserSessionService userSessionService;

    public AuthenticationController(ObjectFactory<RequestContext> requestContextProvider, UserDtoRepository userDtoRepository, PasswordEncryptor passwordEncryptor, IUserSessionService userSessionService) {
        this.requestContextProvider = requestContextProvider;
        this.userDtoRepository = userDtoRepository;
        this.passwordEncryptor = passwordEncryptor;
        this.userSessionService = userSessionService;
    }

    @PostMapping("login")
    public ResponseEntity<UserCredentials> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        UserDto user = userDtoRepository.findByUsername(username);

        if (user == null || !passwordEncryptor.checkPassword(password, user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserCredentials credentials = userSessionService.createAndWriteUserSession(user);

        Cookie authorizationCookie = new Cookie(Constants.AUTHORIZATION_TOKEN_COOKIE, credentials.getRefreshToken());
        authorizationCookie.setHttpOnly(true);
        authorizationCookie.setPath("/api/auth");

        response.addCookie(authorizationCookie);

        return new ResponseEntity<>(credentials, HttpStatus.OK);
    }

    @PostMapping("logout")
    public ResponseEntity logout(@CookieValue(name = refreshTokenCookie) UUID refreshToken, HttpServletResponse response) {
        RequestContext context = requestContextProvider.getObject();
        UserShortSession userShortSession = context.getUser();
        if (userShortSession == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN); // todo: compare with 401-UNAUTHORIZED code
        Optional<UserDto> userDto = userDtoRepository.findById(userShortSession.getUserId());

        if (userDto.isEmpty())
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        userSessionService.invalidateSession(userDto.get(), refreshToken);
        response.addCookie(new Cookie(Constants.AUTHORIZATION_TOKEN_COOKIE, null));

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("refreshToken")
    public ResponseEntity<UserCredentials> refreshToken(@CookieValue(name = refreshTokenCookie) UUID refreshToken, HttpServletResponse response) {
        RequestContext context = requestContextProvider.getObject();
        UserShortSession session = context.getUser();
        if (session == null || refreshToken == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Optional<UserDto> userDto = userDtoRepository.findById(session.getUserId());
        if (userDto.isEmpty())
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        UserCredentials newCredentials = userSessionService.updateUserSession(userDto.get(), refreshToken);

        if (newCredentials == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Cookie authorizationCookie = new Cookie(Constants.AUTHORIZATION_TOKEN_COOKIE, newCredentials.getRefreshToken());
        authorizationCookie.setHttpOnly(true);
        authorizationCookie.setPath("/api/auth");

        response.addCookie(authorizationCookie);

        return new ResponseEntity<>(newCredentials, HttpStatus.OK);
    }

    @PostMapping("addUser")
    public ResponseEntity addUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request) throws UnknownHostException {
        InetAddress clientAddress = InetAddress.getByName(request.getRemoteAddr());

        if (!clientAddress.isLoopbackAddress())
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        userDtoRepository.save(new UserDto(username, passwordEncryptor.encryptPassword(password), "test@example.com"));

        return new ResponseEntity(HttpStatus.OK);
    }
}
