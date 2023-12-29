package com.roovies.member.domain.dto;

import com.roovies.member.domain.entity.Authority;
import com.roovies.member.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateResponse {

    private Long id;

    private String email;

    private String nickname;

    private String name;

    private List<Authority> roles = new ArrayList<>();

    private String token;

    public MemberCreateResponse(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.roles = member.getRoles();
    }
}
