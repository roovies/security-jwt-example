package com.roovies.securityjwtexample.security.service;

import com.roovies.securityjwtexample.member.domain.entity.Member;
import com.roovies.securityjwtexample.member.repository.MemberRepository;
import com.roovies.securityjwtexample.security.domain.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("유효하지 않은 회원입니다!"));
        return new CustomUserDetails(member);
    }
}
