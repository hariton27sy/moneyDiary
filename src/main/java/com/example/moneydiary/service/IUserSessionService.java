package com.example.moneydiary.service;


import com.example.moneydiary.dto.UserDto;
import com.example.moneydiary.dto.UserSessionDto;
import com.example.moneydiary.model.UserCredentials;
import com.example.moneydiary.model.UserShortSession;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * This service gives methods to parse UserSession from accessToken and Refresh token
 */
public interface IUserSessionService {
    @Nullable
    UserShortSession getUserSession(String token);

    @NotNull
    UserCredentials createAndWriteUserSession(UserDto userDto);

    @Nullable
    UserCredentials updateUserSession(UserDto userDto, UUID refreshToken);

    boolean invalidateSession(UserDto userDto, UUID refreshToken);
}
