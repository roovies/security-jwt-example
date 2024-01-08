package com.roovies.securityjwtexample.member.domain.dto;

import com.roovies.securityjwtexample.member.domain.entity.Member;
import com.roovies.securityjwtexample.member.domain.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateResponse {

    private Long id;

    private String email;

    private String nickname;

    private String name;

    private MemberRole memberRole;

    private TokenDTO token;

    public MemberCreateResponse(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.memberRole = member.getMemberRole();
    }
}
