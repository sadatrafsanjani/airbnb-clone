package com.rafsan.rentservice.service;

import org.springframework.security.core.Authentication;

public interface JwtService {

    Long getExpirationTime();
    String generateToken(Authentication authentication);
    String generateTokenWithUsername(String username);
    boolean validateToken(String jwt);
    String getUsernameFromJWT(String token);
}
