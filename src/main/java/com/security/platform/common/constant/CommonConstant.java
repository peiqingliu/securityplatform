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
     * 全部审批权限
     */
    Integer DATA_APPROVAL_ALL = 1;

    /**
     * 全部第三参数权限
     */
    Integer FACTOR_APPROVAL_ALL = 1;

    /**
     * 部分第三参数权限
     */
    Integer FACTOR_APPROVAL_PART = 0;

    /**
     * 全部流程权限
     */
    Integer FLOW_APPROVAL_ALL = 1;


    /**
     * 部分流程权限
     */
    Integer FLOW_APPROVAL_PART = 0;


    /**
     * 数据类型 审批类型
     */
    Integer DATA_ROLE_TYPE = 1;
    /**
     * 数据类型 审批类型
     */
    Integer APPROVAL_ROLE_TYPE = 1;
    /**
     * 数据类型 菜单类型
     */
    Integer MENU_ROLE_TYPE = 0;

    /**
     * 自定义数据权限
     */
    Integer DATA_TYPE_CUSTOM = 1;

    /**
     * 正常状态
     */
    Integer STATUS_NORMAL = 0;

    /**
     * 禁用状态
     */
    Integer STATUS_DISABLE = -1;

    /**
     * 删除标志
     */
    Integer DEL_FLAG = 1;



    /**
     * 状态 待提交申请
     */
    String STATUS_TO_APPLY = "0";


    /**
     * 状态 处理中
     */
    String STATUS_DEALING = "1";

    /**
     * 状态 处理结束
     */
    String STATUS_FINISH = "2";

    /**
     * 状态 已撤回
     */
    String STATUS_CANCELED = "3";


    /**
     * 结果 待提交
     */
    String RESULT_TO_SUBMIT = "0";

    /**
     * 结果 处理中
     */
    String RESULT_DEALING = "1";

    /**
     * 结果 通过
     */
    String RESULT_PASS = "2";

    /**
     * 结果 驳回
     */
    String RESULT_FAIL = "3";

    /**
     * 结果 撤回
     */
    String RESULT_CANCEL = "4";

    /**
     * 结果 删除
     */
    String RESULT_DELETED = "5";



    /**
     * 限流标识
     */
    String LIMIT_ALL = "XBOOT_LIMIT_ALL";

    /**
     * 顶部菜单类型权限
     */
    Integer PERMISSION_NAV = -1;

    /**
     * 页面类型权限
     */
    Integer PERMISSION_PAGE = 1;

    /**
     * 操作类型权限
     */
    Integer PERMISSION_OPERATION = 2;

    /**
     * 1级菜单父id
     */
    String PARENT_ID = "0";

    /**
     * 0级菜单
     */
    Integer LEVEL_ZERO = 0;

    /**
     * 1级菜单
     */
    Integer LEVEL_ONE = 1;

    /**
     * 2级菜单
     */
    Integer LEVEL_TWO = 2;

    /**
     * 3级菜单
     */
    Integer LEVEL_THREE = 3;

    /**
     * 4级
     */
    Integer LEVEL_FOUR = 4;

    /**
     * 消息发送范围
     */
    Integer MESSAGE_RANGE_ALL = 0;

    /**
     * 未读
     */
    Integer MESSAGE_STATUS_UNREAD = 0;

    /**
     * 已读
     */
    Integer MESSAGE_STATUS_READ = 1;


    /**
     * 短信验证码key前缀
     */
    String PRE_SMS = "CETC_PRE_SMS:";

    /**
     * 邮件验证码key前缀
     */
    String PRE_EMAIL = "CETC_PRE_EMAIL:";

    /**
     * 本地文件存储
     */
    Integer OSS_LOCAL = 0;

    /**
     * 菜单父主键默认值
     */
    String DEFAULT_VALUE = "0";


    /**
     * 父主键默认值
     */
    String DEFAULT_PARENT_VALUE = "0";

    /**
     * 蚌埠
     */
    String BENG_BU =   "蚌埠";

    /**
     * 青岛
     */
    String QING_DAO =   "青岛";

}
