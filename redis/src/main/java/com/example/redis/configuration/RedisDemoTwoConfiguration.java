package com.example.redis.configuration;


import com.example.redis.serializer.HashRedisSerializer;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisDemoTwoConfiguration {

    private final StringRedisSerializer stringRedisSerializer;
    private final HashRedisSerializer hashRedisSerializer;

    @Value("${redis.redis-demo-two.host}")
    private String host;
    @Value("${redis.redis-demo-two.port}")
    private int port;
    @Value("${redis.redis-demo-two.lettuce.pool.max-active}")
    private int maxActive;
    @Value("${redis.redis-demo-two.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${redis.redis-demo-two.lettuce.pool.min-idle}")
    private int minIdle;
    @Value("${redis.redis-demo-two.lettuce.pool.max-wait}")
    private int maxWait;

    public RedisDemoTwoConfiguration() {
        stringRedisSerializer = new StringRedisSerializer();
        hashRedisSerializer = new HashRedisSerializer();
    }

    @Bean("demoTwoRedisTemplate")
    RedisTemplate<String, Object> redisTemplate(@Qualifier("demoTwoConnectionFactory") RedisConnectionFactory rcf) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(rcf);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(hashRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @SuppressWarnings("rawtypes")
    @Bean("demoTwoConnectionFactory")
    public RedisConnectionFactory connectionFactory(
            @Qualifier("demoTwoRedisStandaloneConfiguration") RedisStandaloneConfiguration redisStandaloneConfiguration,
            @Qualifier("demoTwoRedisPool") GenericObjectPoolConfig pool) {
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder().poolConfig(pool).build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
        factory.setShareNativeConnection(false);
        return factory;
    }

    @SuppressWarnings("rawtypes")
    @Bean("demoTwoRedisPool")
    GenericObjectPoolConfig createGenericPool() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        return genericObjectPoolConfig;
    }

    @Bean("demoTwoRedisStandaloneConfiguration")
    RedisStandaloneConfiguration redisStandaloneConfiguration() {
        return new RedisStandaloneConfiguration(host, port);
    }

}
