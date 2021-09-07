package com.authenticator.service;

import com.authenticator.dto.request.EmailRequest;
import com.authenticator.dto.request.LoginRequest;
import com.authenticator.dto.request.RefreshTokenRequest;
import com.authenticator.dto.request.UsernameRequest;
import com.authenticator.dto.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);
    LoginResponse refreshToken(RefreshTokenRequest request);
    boolean checkEmailIfExists(EmailRequest request);
    boolean checkUsernameIfExists(UsernameRequest request);
}
