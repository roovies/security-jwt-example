package com.roovies.securityjwtexample.member.domain.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDTO {
    private String access_token;
    private String refresh_token;
}
