package com.example.moneydiary.filter;

import com.example.moneydiary.Constants;
import com.example.moneydiary.model.RequestContext;
import com.example.moneydiary.service.IUserSessionService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * Try to get access token from request and authenticate user by this token
 */
@Component
@Order(1)
public class AuthenticationFilter extends OncePerRequestFilter {
    private final String AuthorizationHeader = "Authorization";
    private final String AuthorizationType = "Bearer ";


    private final ObjectFactory<RequestContext> requestContextProvider;
    private IUserSessionService userSessionService;

    public AuthenticationFilter(ObjectFactory<RequestContext> requestContextProvider, IUserSessionService userSessionService) {
        this.requestContextProvider = requestContextProvider;
        this.userSessionService = userSessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Try to get token
        logger.info("Authentication filter");
        String accessToken = getAccessToken(request);

        RequestContext requestContext = requestContextProvider.getObject();
        requestContext.setUser(userSessionService.getUserSession(accessToken));

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = getAccessTokenFromCookie(request);
        if (accessToken != null)
            return accessToken;

        accessToken = request.getHeader(AuthorizationHeader);
        if (accessToken != null && !accessToken.isBlank())
            return accessToken.replace(AuthorizationType, "").trim();

        return null;
    }

    private String getAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;

        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), Constants.ACCESS_TOKEN_COOKIE)) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
