package com.security.platform.common.constant;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/10 15:22
 * @description 授权校验常量
 */
public interface SecurityConstant {


    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = "cetc";



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


    /**
     * redis缓存token前缀，交互token前缀key
     */
    String TOKEN_PRE = "CETC_TOKEN_PRE:";


    /**
     * 用户token前缀key 单点登录使用
     */
    String USER_TOKEN = "CETC_USER_TOKEN:";

    /**
     * 用户选择JWT保存时间参数头
     */
    String SAVE_LOGIN = "saveLogin";


    /**
     * 用户登录错误次数
     */
    String LOGINTIME_LIMIT = "loginTimeLimit:";

}
