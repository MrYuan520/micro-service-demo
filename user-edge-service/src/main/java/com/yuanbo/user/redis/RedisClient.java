package com.yuanbo.user.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author boyuan
 * @create 2018-08-30 16:45
 */
@Component
public class RedisClient {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key){
        return (T)redisTemplate.opsForValue().get(key);
    }

    public void set(String key,Object object){
        redisTemplate.opsForValue().set(key,object);
    }

    public void set(String key,Object object,int tiemout){
        redisTemplate.opsForValue().set(key,object,tiemout,TimeUnit.SECONDS);
    }

    public void expire(String key,int timeout){
        redisTemplate.expire(key,timeout, TimeUnit.SECONDS);
    }
}
