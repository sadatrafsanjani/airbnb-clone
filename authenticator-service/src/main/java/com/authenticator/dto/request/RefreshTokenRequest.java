package com.authenticator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenRequest {

    private String username;
    private String refreshToken;
}
