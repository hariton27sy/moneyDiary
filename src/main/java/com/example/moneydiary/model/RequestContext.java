package com.example.moneydiary.model;

import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class RequestContext {
    private @Nullable
    UserShortSession user;

    public @Nullable
    UserShortSession getUser() {
        return user;
    }

    public void setUser(@Nullable UserShortSession user) {
        this.user = user;
    }
}
