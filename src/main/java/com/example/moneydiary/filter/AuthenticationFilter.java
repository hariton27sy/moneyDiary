package com.example.moneydiary.filter;

import com.example.moneydiary.model.RequestContext;
import com.example.moneydiary.model.UserShortSession;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Try to get access token from request and authenticate user by this token
 */
@Component
@Order(1)
public class AuthenticationFilter extends OncePerRequestFilter {
    private final String AuthorizationHeader = "Authorization";
    private final String AuthorizationType = "Bearer ";


    private final ObjectFactory<RequestContext> requestContextProvider;

    public AuthenticationFilter(ObjectFactory<RequestContext> requestContextProvider) {
        this.requestContextProvider = requestContextProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Try to get token
        logger.info("Authentication filter");
        String authorizationString = request.getHeader(AuthorizationHeader);
        if (authorizationString != null && !authorizationString.isBlank()){
            authorizationString = authorizationString.replace(AuthorizationType, "").trim();
        }

        RequestContext requestContext = requestContextProvider.getObject();
        requestContext.setUser(new UserShortSession());

        filterChain.doFilter(request, response);
    }
}
