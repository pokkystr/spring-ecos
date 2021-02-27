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
public class RedisDemoOneConfiguration {

    private final StringRedisSerializer stringRedisSerializer;
    private final HashRedisSerializer hashRedisSerializer;

    @Value("${redis.redis-demo-one.host}")
    private String host;
    @Value("${redis.redis-demo-one.port}")
    private int port;
    @Value("${redis.redis-demo-one.lettuce.pool.max-active}")
    private int maxActive;
    @Value("${redis.redis-demo-one.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${redis.redis-demo-one.lettuce.pool.min-idle}")
    private int minIdle;
    @Value("${redis.redis-demo-one.lettuce.pool.max-wait}")
    private int maxWait;

    public RedisDemoOneConfiguration() {
        stringRedisSerializer = new StringRedisSerializer();
        hashRedisSerializer = new HashRedisSerializer();
    }

    @Bean("demoOneRedisTemplate")
    RedisTemplate<String, Object> redisTemplate(@Qualifier("demoOneConnectionFactory") RedisConnectionFactory rcf) {
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
    @Bean("demoOneConnectionFactory")
    public RedisConnectionFactory connectionFactory(
            @Qualifier("demoOneRedisStandaloneConfiguration") RedisStandaloneConfiguration redisStandaloneConfiguration,
            @Qualifier("demoOneRedisPool") GenericObjectPoolConfig pool) {
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder().poolConfig(pool).build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
        factory.setShareNativeConnection(false);
        return factory;
    }

    @SuppressWarnings("rawtypes")
    @Bean("demoOneRedisPool")
    GenericObjectPoolConfig createGenericPool() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        return genericObjectPoolConfig;
    }


    @Bean("demoOneRedisStandaloneConfiguration")
    RedisStandaloneConfiguration redisStandaloneConfiguration() {
        return new RedisStandaloneConfiguration(host, port);
    }

}
