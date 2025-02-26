package webdev.e_commerce.cart_service.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public class CurrentAuthContext {
    private static Map<String, Object> extractClaim() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Map<String, Object> claims = ((Jwt) principal).getClaims();

        return claims;
    }

    public static String getUserID() {
        return (String) extractClaim().get("sub");
    }
}