package com.example.moneydiary.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.moneydiary.dto.UserDto;
import com.example.moneydiary.dto.UserSessionDto;
import com.example.moneydiary.model.UserCredentials;
import com.example.moneydiary.model.UserShortSession;
import com.example.moneydiary.repository.UserSessionDtoRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class UserSessionService implements IUserSessionService{
    private final int accessTokenExpirationSeconds;
    private final int refreshTokenExpirationSeconds;
    private final JWTVerifier jwtVerifier;
    private final Algorithm algorithm;
    private final UserSessionDtoRepository userSessionDtoRepository;

    public UserSessionService(UserSessionDtoRepository userSessionDtoRepository) {
        this.userSessionDtoRepository = userSessionDtoRepository;
        accessTokenExpirationSeconds = 600;
        refreshTokenExpirationSeconds = 60 * 24 * 60 * 60;
        String jwtSecretString = "secret";
        algorithm = Algorithm.HMAC512(jwtSecretString);
        jwtVerifier = JWT.require(algorithm)
                .build();
    }

    @Nullable
    @Override
    public UserShortSession getUserSession(String token) {
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            return new UserShortSession(jwt.getClaim("userId").asLong(), jwt.getClaim("username").asString(), jwt.getClaim("expiresIn").asDate(), token);
        } catch (JWTVerificationException | NullPointerException e) {
            return null;
        }
    }

    @Override
    public UserCredentials createAndWriteUserSession(UserDto userDto) {
        Date now = new Date();
        UUID refreshToken = UUID.randomUUID(); // todo: use securityRandom to generate UUID

        UserSessionDto userSession = new UserSessionDto(refreshToken, DateUtils.addSeconds(now, refreshTokenExpirationSeconds), userDto);
        userSessionDtoRepository.save(userSession);
        String accessToken = JWT.create()
                .withClaim("userId", userDto.getUserId())
                .withClaim("username", userDto.getUsername())
                .withClaim("expiresIn", DateUtils.addSeconds(now, accessTokenExpirationSeconds))
                .sign(algorithm);

        return new UserCredentials(accessToken, refreshToken.toString());
    }

    @Nullable
    @Override
    public UserCredentials updateUserSession(UserDto user, UUID refreshToken) {
        if (!tryRemoveOldSession(user, refreshToken))
            return null;

        return createAndWriteUserSession(user);
    }

    @Override
    public boolean invalidateSession(UserDto userDto, UUID refreshToken) {
        return tryRemoveOldSession(userDto, refreshToken);
    }

    private boolean tryRemoveOldSession(UserDto userDto, UUID refreshToken) {
        UserSessionDto userSession = userSessionDtoRepository.findByRefreshTokenAndUser(refreshToken, userDto);
        if (userSession == null)
            return false;

        userSessionDtoRepository.delete(userSession);
        return true;
    }
}
