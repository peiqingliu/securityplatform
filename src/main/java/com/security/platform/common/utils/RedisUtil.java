package com.security.platform.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/9 13:49
 * @File RedisUtil
 * @Software IntelliJ IDEA
 * @description todo
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 缓存AccessToken
     * @param redisKey
     * @param token
     * @param expireTime
     * @param timeUnit
     */
    public void setCacheAccessToken(String redisKey, String token, long expireTime, TimeUnit timeUnit) {
        ValueOperations<String,String> ho = redisTemplate.opsForValue();
        //存储用户资源权限到redis，3天失效
        ho.set(redisKey, token, expireTime, timeUnit);
    }

    /**
     * 缓存中读取AccessToken
     * @param redisKey
     * @return
     */
    public String getCacheAccessToken(String redisKey) {
        ValueOperations<String, String> ho = redisTemplate.opsForValue();
        return ho.get(redisKey);
    }

    /**
     * 保存截图图片
     * @param redisKey
     * @param pictureUrl
     * @param expireTime
     * @param timeUnit
     */
    public void setPictureUrl(String redisKey,String pictureUrl,long expireTime, TimeUnit timeUnit){
        ValueOperations<String,String> ho = redisTemplate.opsForValue();
        //存储用户资源权限到redis
        ho.set(redisKey, pictureUrl, expireTime, timeUnit);
    }

    /**
     * 缓存中读取
     * @param redisKey
     * @return
     */
    public String getPictureUrl(String redisKey) {
        ValueOperations<String, String> ho = redisTemplate.opsForValue();
        return ho.get(redisKey);
    }

}
