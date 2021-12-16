package com.example.moneydiary.controller;

import com.example.moneydiary.Constants;
import com.example.moneydiary.dto.UserDto;
import com.example.moneydiary.model.RequestContext;
import com.example.moneydiary.model.UserCredentials;
import com.example.moneydiary.model.UserShortSession;
import com.example.moneydiary.repository.UserDtoRepository;
import com.example.moneydiary.service.IUserSessionService;
import com.example.moneydiary.service.PasswordEncryptor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
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

    @GetMapping("login")
    public ResponseEntity<UserCredentials> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response, @RequestParam(required = false, name = Constants.REDIRECT_PATH_QUERY) String redirectPath) {
        UserDto user = userDtoRepository.findByEmail(username);

        if (user == null || !passwordEncryptor.checkPassword(password, user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserCredentials credentials = userSessionService.createAndWriteUserSession(user);

        return setCookieAndReturnResponse(credentials, response, redirectPath);
    }

    @GetMapping("logout")
    public ResponseEntity logout(@CookieValue(name = Constants.REFRESH_TOKEN_COOKIE) UUID refreshToken, HttpServletResponse response) {
        RequestContext context = requestContextProvider.getObject();
        UserShortSession userShortSession = context.getUser();
        if (userShortSession == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN); // todo: compare with 401-UNAUTHORIZED code
        Optional<UserDto> userDto = userDtoRepository.findById(userShortSession.getUserId());

        if (userDto.isEmpty())
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        userSessionService.invalidateSession(userDto.get(), refreshToken);
        response.addCookie(new Cookie(Constants.REFRESH_TOKEN_COOKIE, null));
        response.addCookie(new Cookie(Constants.ACCESS_TOKEN_COOKIE, null));

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("refreshToken")
    public ResponseEntity<UserCredentials> refreshToken(@CookieValue(name = refreshTokenCookie) UUID refreshToken, @RequestParam(required = false, name = Constants.REDIRECT_PATH_QUERY) String redirectPath, HttpServletResponse response) {
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

        return setCookieAndReturnResponse(newCredentials, response, redirectPath);
    }

    @PostMapping("addUser")
    public ResponseEntity addUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request) throws UnknownHostException {
        InetAddress clientAddress = InetAddress.getByName(request.getRemoteAddr());

        if (!clientAddress.isLoopbackAddress())
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        userDtoRepository.save(new UserDto(username, passwordEncryptor.encryptPassword(password), "test@example.com"));

        return new ResponseEntity(HttpStatus.OK);
    }

    private ResponseEntity<UserCredentials> setCookieAndReturnResponse(UserCredentials credentials, HttpServletResponse response, String redirectPath) {
        Cookie refreshCookie = new Cookie(Constants.REFRESH_TOKEN_COOKIE, credentials.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/api/auth");

        response.addCookie(refreshCookie);
        Cookie accessCookie = new Cookie(Constants.ACCESS_TOKEN_COOKIE, credentials.getAccessToken());
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        if (redirectPath != null){
            response.setHeader(Constants.LocationHeader, redirectPath);
            return new ResponseEntity<>(credentials, HttpStatus.TEMPORARY_REDIRECT);
        }

        return new ResponseEntity<>(credentials, HttpStatus.OK);
    }
}
