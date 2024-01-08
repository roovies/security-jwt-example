package com.roovies.securityjwtexample.member.domain.entity;

import com.roovies.securityjwtexample.member.domain.enums.MemberRole;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String nickname;

    private String name;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole memberRole;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
