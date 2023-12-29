package com.roovies.securityjwtexample.member.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateRequest {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String name;
}
