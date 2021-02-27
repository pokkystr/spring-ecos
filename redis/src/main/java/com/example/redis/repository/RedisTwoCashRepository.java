package com.example.redis.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
@Slf4j
public class RedisTwoCashRepository extends AbstractRedis {

    protected RedisTwoCashRepository(@Qualifier("demoTwoRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    public boolean isExists(String citizen) {
        return Optional.ofNullable(getValue(citizen)).isPresent();
    }

    public Optional<String> get(String citizen) {
        Object value = getValue(citizen);
        return Optional.ofNullable((String) value);
    }

    public void set(String citizen, String value) {
        setValue(citizen, value);
    }

    public void delete(String key) {
        deleteKey(key);
    }
}
