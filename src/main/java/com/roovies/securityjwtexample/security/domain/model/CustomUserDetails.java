package com.roovies.securityjwtexample.security.domain.model;

import com.roovies.securityjwtexample.member.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String email;
    private String password;
    private Long memberId;

    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return member.getRoles().stream().map(o -> new SimpleGrantedAuthority(o.getName())).collect(Collectors.toList());
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
