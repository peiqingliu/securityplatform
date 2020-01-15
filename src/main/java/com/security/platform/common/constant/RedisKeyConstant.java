package com.security.platform.common.constant;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/14 11:55
 * @File RedisKeyConstant
 * @Software IntelliJ IDEA
 * @description 缓存相关
 */
public interface RedisKeyConstant {

    /**
     * 截图
     */
    String pictureUrlKey = "picture";

    /**
     * 是否开启注册服务
     */
    String startService = "startService";

    /**
     * 服务开启状态标识
     */
    String opened  = "opened";

    /**
     * 服务关闭状态标识
     */
    String closed = "closed";
}
