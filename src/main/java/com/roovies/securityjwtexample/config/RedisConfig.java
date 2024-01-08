package com.roovies.securityjwtexample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Spring Framework와 Redis 서버의 연결 설정하는 빈을 생성하고 반환
        return new LettuceConnectionFactory(host, port); // LettuceConnectionFactory는 RedisConnectionFactory의 구현체 중 하나
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        // RedisTemplate : Redis 데이터를 쉽게 조작하고 관리할 수 있도록 Spring에서 제공
        //                 즉, RedisTemplate을 이용하여 Redis에 CRUD 작업 수행
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();

        // Key, Value 직렬화(Serialize)
        // 이걸 수행하지 않으면, Spring에서는 조회할 때 값이 정상적으로 보이지만, redis-cli로 보면 \xac\xed\x00 같은 값들이 붙는다.
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        // Hash를 사용할 경우 시리얼라이저
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        // 모든 경우
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());

        // 생성된 연결 팩토리를 통해 Redis와 상호작용할 수 있도록 함
        // 즉 RedisTemplate은 LettuceConnectionFactory로 생성된 연결을 이용하여 데이터 송수신을 지원
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
