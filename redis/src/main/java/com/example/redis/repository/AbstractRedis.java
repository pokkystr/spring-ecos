package com.example.redis.repository;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public abstract class AbstractRedis {

    protected RedisTemplate<String, Object> redisTemplate;

    protected AbstractRedis(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    protected void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    protected void setValue(String key, Object value, long duration) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(duration));
    }

    protected void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    protected Object getHashObject(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    protected void setHashObject(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    protected void setHashObject(String key, String hashKey, Object value, long duration) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        redisTemplate.expire(key, duration, TimeUnit.MINUTES);
    }

    protected void increment(String key, String hashKey, int value, long duration) {
        redisTemplate.opsForHash().increment(key, hashKey, value);
        redisTemplate.expire(key, duration, TimeUnit.MINUTES);
    }
}
