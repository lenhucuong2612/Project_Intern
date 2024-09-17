package com.example.springtestsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String TOKEN_SET="tokens";
    @Value("${auth.token.expirationInMils}")
    private int jwtExpirationMs;
    //save token for redis
    public void saveToken(String token){
        long ttlMs=jwtExpirationMs;
        redisTemplate.opsForSet().add(TOKEN_SET,token);
        redisTemplate.expire(TOKEN_SET, ttlMs, TimeUnit.MILLISECONDS);
    }
    //check token in redis
    public boolean isTokenExists(String token){
        return redisTemplate.opsForSet().isMember(TOKEN_SET, token);
    }
}
