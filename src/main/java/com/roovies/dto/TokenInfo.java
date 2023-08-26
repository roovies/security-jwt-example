package com.roovies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenInfo {
    private String grantType; // JWT에 대한 인증 타입으로, Bearer를 사용할 것이다.
    private String accessToken;
    private String refreshToken;
}
