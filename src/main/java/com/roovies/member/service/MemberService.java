package com.roovies.member.service;

import com.roovies.member.domain.dto.MemberCreateRequest;
import com.roovies.member.domain.dto.MemberCreateResponse;
import com.roovies.member.domain.entity.Authority;
import com.roovies.member.domain.entity.Member;
import com.roovies.member.repository.MemberRepository;
import com.roovies.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public MemberCreateResponse login(MemberCreateRequest request) throws Exception {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("잘못된 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }

        return MemberCreateResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getEmail())
                .nickname(member.getNickname())
                .roles(member.getRoles())
                .token(jwtProvider.createToken(member.getEmail(), member.getRoles()))
                .build();
    }

    public boolean register(MemberCreateRequest request) throws Exception {
        try {
            Member member = Member.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .nickname(request.getNickname())
                    .build();

            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            memberRepository.save(member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }

    public MemberCreateResponse getMemberByEmail(String email) throws Exception {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new MemberCreateResponse(member);
    }
}
