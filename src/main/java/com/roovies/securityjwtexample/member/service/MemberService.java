package com.roovies.securityjwtexample.member.service;

import com.roovies.securityjwtexample.member.domain.dto.MemberCreateRequest;
import com.roovies.securityjwtexample.member.domain.dto.MemberCreateResponse;
import com.roovies.securityjwtexample.member.domain.dto.TokenDTO;
import com.roovies.securityjwtexample.member.domain.entity.Member;
import com.roovies.securityjwtexample.member.domain.enums.MemberRole;
import com.roovies.securityjwtexample.member.repository.MemberRepository;
import com.roovies.securityjwtexample.security.jwt.JwtProvider;
import com.roovies.securityjwtexample.security.jwt.RefreshToken;
import com.roovies.securityjwtexample.security.jwt.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public MemberCreateResponse login(MemberCreateRequest request) throws Exception {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("잘못된 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }

        member.setRefreshToken(createRefreshToken(member));

        return MemberCreateResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getEmail())
                .nickname(member.getNickname())
                .memberRole(member.getMemberRole())
                .token(TokenDTO.builder()
                        .access_token(jwtProvider.createToken(member.getEmail(), member.getMemberRole()))
                        .refresh_token(member.getRefreshToken())
                        .build())
                .build();
    }

    public boolean register(MemberCreateRequest request) throws Exception {
        try {
            Member member = Member.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .nickname(request.getNickname())
                    .memberRole(MemberRole.ROLE_USER)
                    .build();

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

    /**
     * Refresh Token
     */
    // Refresh Token 생성
    public String createRefreshToken(Member member) {
        RefreshToken refreshToken = refreshTokenRepository.save(
                RefreshToken.builder()
                        .id(member.getId())
                        .refreshToken(UUID.randomUUID().toString())
                        .ttl(120)
                        .build()
        );
        return refreshToken.getRefreshToken();
    }

    // Refresh Token 검증
    public RefreshToken validRefreshToken(Member member, String refreshToken) throws Exception {
        // 해당 유저의 Refresh 토큰이 DB에 없으면, TTL에 의해 만료된 것
        RefreshToken foundRefreshToken = refreshTokenRepository.findById(member.getId()).orElseThrow(
                () -> new Exception("Refresh Token이 만료되었습니다. 로그인을 다시하세요.")
        );
        
        // 토큰이 일치하는지 비교
        if (foundRefreshToken.getRefreshToken() == null)
            return null;
        else {
            if (!foundRefreshToken.getRefreshToken().equals(refreshToken)) 
                return null;
            else 
                return foundRefreshToken;
        }
    }

    // 전송받은 Token DTO 처리
    public TokenDTO refreshAccessToken(TokenDTO token) throws Exception {
        // access token 검증
        String email = jwtProvider.getEmail(token.getAccess_token());
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new BadCredentialsException("토큰 내에 계정 정보가 잘못되었습니다."));

        // refresh token 검증
        RefreshToken refreshToken = validRefreshToken(member, token.getRefresh_token());
        if (refreshToken != null) {
            return TokenDTO.builder()
                    .access_token(jwtProvider.createToken(email, member.getMemberRole()))
                    .refresh_token(refreshToken.getRefreshToken())
                    .build();
        } else {
            throw new Exception("로그인이 필요합니다.");
        }
    }
}
