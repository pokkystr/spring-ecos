package com.example.redis.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisOneCashRepositoryTests {

    @Autowired
    private RedisOneCashRepository redisOneCashRepository;

    private String key = "1234";

    @Test
    void test_set_get_value() {
        redisOneCashRepository.set(key, "HELLO");
        Assert.assertTrue(redisOneCashRepository.get(key).isPresent());
        String value = redisOneCashRepository.get(key).get();
        Assert.assertEquals("HELLO", value);
    }
}
