package com.security.plateform.common.constant;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/11/30 11:29
 * @description 系统公共常量
 */
public interface CommonConstant {


    /**
     * 用户默认头像
     */
    String USER_DEFAULT_AVATAR = "https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png";

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
     * 用户正常状态
     */
    Integer USER_STATUS_NORMAL = 0;


    /**
     * 全部数据权限
     */
    Integer DATA_TYPE_ALL = 0;

    /**
     * 操作类型权限
     */
    Integer PERMISSION_OPERATION = 2;

    /**
     * 用户禁用状态
     */
    Integer USER_STATUS_LOCK = -1;

}
