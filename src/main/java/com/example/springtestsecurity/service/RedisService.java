package com.example.springtestsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${auth.token.expirationInMils}")
    private int jwtExpirationMs;
    //save token for redis
    public void saveToken(String name,String token){
        long ttlMs=jwtExpirationMs;
        redisTemplate.opsForValue().set(name,token);
        redisTemplate.expire(name, ttlMs, TimeUnit.MILLISECONDS);
    }
    //check token in redis
    public boolean isTokenExists(String keyName){
        String token=redisTemplate.opsForValue().get(keyName);
        return token != null;
    }
public boolean deleteToken(String keyName) {
    String value = redisTemplate.opsForValue().get(keyName);
    if (value != null) {
        redisTemplate.delete(keyName);
        return true;
    }
    return false;
}

}
