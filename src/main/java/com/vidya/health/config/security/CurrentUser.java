package com.vidya.health.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class CurrentUser {
    private CurrentUser() {}

    public static String emailOrAnonymous() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return (a == null) ? "anonymous" : String.valueOf(a.getPrincipal());
    }

    public static JwtService.JwtClaims claimsOrNull() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null) return null;
        Object details = a.getDetails();
        if (details instanceof JwtService.JwtClaims c) return c;
        return null;
    }
}
