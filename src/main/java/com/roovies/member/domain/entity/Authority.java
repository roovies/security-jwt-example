package com.roovies.member.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String name;

    @JoinColumn(name = "member")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }
}
