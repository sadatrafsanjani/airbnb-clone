package com.authenticator.controller;

import com.authenticator.dto.request.*;
import com.authenticator.dto.response.LoginResponse;
import com.authenticator.service.AuthenticationService;
import com.authenticator.service.RefreshTokenService;
import com.authenticator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authenticator/authentication")
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private UserService userService;
    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService,
                                    UserService userService,
                                    RefreshTokenService refreshTokenService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        LoginResponse response = authenticationService.login(request);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshTokens(@RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {

        refreshTokenService.deleteRefreshToken(request.getRefreshToken());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/check/username")
    public ResponseEntity checkUsername(@RequestBody UsernameRequest request){

        if(authenticationService.checkUsernameIfExists(request)){

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/check/email")
    public ResponseEntity checkEmail(@RequestBody EmailRequest request){

        if(authenticationService.checkEmailIfExists(request)){

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request){

        if(userService.register(request)){

            return ResponseEntity.ok("Registration Successful!");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/verify/{id}")
    public ResponseEntity<String> verifyUser(@PathVariable("id") long id){

        userService.verifyEmail(id);

        return ResponseEntity.ok().build();
    }
}