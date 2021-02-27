package com.example.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.UnsupportedEncodingException;

public class HashRedisSerializer implements RedisSerializer<Object> {

    private static final String ENCODE = "UTF-8";

    @Override
    public byte[] serialize(Object t) throws SerializationException {
        byte[] result = null;
        try {
            if (t != null) {
                String toStr = t.toString();
                result = toStr.getBytes(ENCODE);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        String result = null;
        try {
            if (bytes != null) {
                result = new String(bytes, ENCODE);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
