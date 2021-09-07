package com.authenticator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {

    private Long id;
    private String username;
    private Set<Long> roles;
    private String loginToken;
    private String refreshToken;
    private Instant expiresAt;
}
