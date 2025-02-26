package webdev.e_commerce.order_service.utils;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public class CurrentAuthContext {

    private static Map<String, Object> extractClaim() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication is not valid");
        }
        Object principal = authentication.getPrincipal();
        return ((Jwt) principal).getClaims();
    }

    public static String getUserID() {
        Map<String, Object> claims = extractClaim();
        return claims != null ? (String) claims.get("sub") : null;
    }

    public static String getUserEmail() {
        Map<String, Object> claims = extractClaim();
        return claims != null ? (String) claims.get("email") : null;
    }
}