package com.security.platform.common.constant;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 11:21
 * @description TODO
 */
public interface CommonConstant {


    /**
     * 用户默认头像
     */
    String USER_DEFAULT_AVATAR = "https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png";



    /**
     * 用户正常状态
     */
    Integer USER_STATUS_NORMAL = 0;

    /**
     * 用户禁用状态
     */
    Integer USER_STATUS_LOCK = -1;

    /**
     * 正常状态
     */
    Integer STATUS_NORMAL = 0;

    /**
     * 普通用户
     */
    Integer USER_TYPE_NORMAL = 0;

    /**
     * 管理员
     */
    Integer USER_TYPE_ADMIN = 1;

    /**
     * 全部数据权限
     */
    Integer DATA_TYPE_ALL = 0;

    /**
     * 操作类型权限
     */
    Integer PERMISSION_OPERATION = 2;

    /**
     * 日志类型：登录
     */
    Integer LOG_TYPE_LOGIN = 0;

    /**
     * 日志类型：远程接口截图
     */
    Integer LOG_TYPE_OPERAT = 1;


}
