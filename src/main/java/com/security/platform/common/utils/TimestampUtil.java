package com.security.platform.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/9 13:48
 * @File TimestampUtil
 * @Software IntelliJ IDEA
 * @description 时间戳验证
 */
@Component
public class TimestampUtil {

    @Value("${us.tickerExpireTime}")
    private int tickerExpireTime;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 验证时间戳是否合法
     * @param cacheKey
     * @param currentTimestamp
     * @return
     */
    public  Boolean validateTimestamp(String cacheKey,long currentTimestamp) {
        long beforeTimeStamp = 0;
        String timestamp = redisUtil.getCacheAccessToken(cacheKey);
        if(timestamp != null && timestamp != "") {
            beforeTimeStamp = Long.parseLong(timestamp);
        }
         //当前时间戳小于或等于之前的时间戳。说明是重复的
        if(currentTimestamp < beforeTimeStamp && beforeTimeStamp > 0) {
            return false;
        }
        //session可能会超时
        long nowTimeStamp = System.currentTimeMillis();
        if(nowTimeStamp - currentTimestamp > tickerExpireTime * 60 * 1000 ) {
            return false;
        }
        //redis存储的key,value,失效时间，时间单位
        redisUtil.setCacheAccessToken(cacheKey, String.valueOf(currentTimestamp), tickerExpireTime, TimeUnit.MINUTES);

        return true;

    }

    /**
     * 精确到秒的时间戳
     * @param date
     * @return
     */
    private static long getSecondTimestampTwo(Date date) {
        if(null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime()/1000);
        return Long.parseLong(timestamp);
    }
}
