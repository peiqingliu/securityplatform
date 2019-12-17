package com.security.plateform.common.constant;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/5 15:02
 * @description 授权校验常量
 */
public interface SecurityConstant {


    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = "SecurityPlatform";


    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

    /**
     * token分割
     */
    String TOKEN_SPLIT = "Bearer ";


    /**
     * token参数头
     */
    String HEADER = "accessToken";
}
