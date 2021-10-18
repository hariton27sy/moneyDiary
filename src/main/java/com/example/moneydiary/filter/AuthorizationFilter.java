package com.example.moneydiary.filter;

import com.example.moneydiary.model.RequestContext;
import com.example.moneydiary.model.UserShortSession;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Check rights for common paths. You need implement custom checking rights to authorize users for certain resources.
 */
@Component
@Order(2)
public class AuthorizationFilter extends OncePerRequestFilter {
    private final ObjectFactory<RequestContext> requestContextProvider;

    public AuthorizationFilter(ObjectFactory<RequestContext> requestContextProvider) {
        this.requestContextProvider = requestContextProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        UserShortSession user = getContext().getUser();
//        if (user == null || user.isExpired()) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return;
//        }
        // Now any user can get access to any controller;
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth") || path.startsWith("/swagger");
    }

    @NotNull
    private RequestContext getContext() {
        return requestContextProvider.getObject();
    }
}
