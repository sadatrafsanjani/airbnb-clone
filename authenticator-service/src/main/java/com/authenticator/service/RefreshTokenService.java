package com.authenticator.service;

import com.authenticator.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();
    void validateRefreshToken(String token);
    void deleteRefreshToken(String token);
}
