package com.roovies.securityjwtexample.security.jwt;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "refresh_token")
public class RefreshToken {
    @Id
    private Long id;

    @Indexed //값으로 검색할 시 필요한 어노테이션
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Integer ttl;

    public void setTTL(Integer ttl) {
        this.ttl = ttl;
    }
}
