package com.security.platform.modules.system.service;

import com.security.platform.base.BaseService;
import com.security.platform.modules.system.entity.User;
import com.security.platform.modules.system.entity.UserRole;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/25 12:51
 * @description 用户角色接口
 */

public interface UserRoleService extends BaseService<UserRole,String> {


    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(String roleId);

    /**
     * 获取用户拥有的所有权限
     * @return
     */
    List<UserRole> findByUserId(String userId);

    /**
     * 删除用户角色
     * @param userId
     */
    void deleteByUserId(String userId);

}
