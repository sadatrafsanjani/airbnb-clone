package com.authenticator.service;

import com.authenticator.dto.request.EmailRequest;
import com.authenticator.dto.request.LoginRequest;
import com.authenticator.dto.request.RefreshTokenRequest;
import com.authenticator.dto.request.UsernameRequest;
import com.authenticator.dto.response.LoginResponse;
import com.authenticator.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private UserService userService;
    private RoleService roleService;
    private RefreshTokenService refreshTokenService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     RoleService roleService,
                                     RefreshTokenService refreshTokenService,
                                     AuthenticationManager authenticationManager,
                                     JwtService jwtService) {
        this.userService = userService;
        this.roleService = roleService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request){

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        try{
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String authenticationToken = jwtService.generateToken(authentication);
            User user = userService.findByUsername(request.getUsername());

            return LoginResponse.builder()
                    .id(user.getId())
                    .username(request.getUsername())
                    .roles(userService.getRoles(user))
                    .loginToken(authenticationToken)
                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                    .expiresAt(Instant.now().plusMillis(jwtService.getExpirationTime()))
                    .build();

        }
        catch (Exception e){
            logger.info(e.getMessage());
        }

        return null;
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request){

        refreshTokenService.validateRefreshToken(request.getRefreshToken());
        String token = jwtService.generateTokenWithUsername(request.getUsername());
        User user = userService.findByUsername(request.getUsername());

        byte[] picture = user.getPicture();

        return LoginResponse.builder()
                .id(user.getId())
                .username(request.getUsername())
                .roles(userService.getRoles(user))
                .loginToken(token)
                .refreshToken(request.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtService.getExpirationTime()))
                .build();
    }


    @Override
    public boolean checkEmailIfExists(EmailRequest request){

        return userService.findByEmail(request.getEmail()) != null;
    }

    @Override
    public boolean checkUsernameIfExists(UsernameRequest request){

        return userService.findByUsername(request.getUsername()) != null;
    }
}
