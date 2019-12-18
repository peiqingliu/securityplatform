package com.security.platform.modules.system.dao;

import com.security.platform.base.BaseDao;
import com.security.platform.modules.system.entity.UserRole;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/6 15:58
 * @description 人员角色关系数据处理层
 */
public interface UserRoleDao extends BaseDao<UserRole,String> {

    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(String roleId);

    /**
     * 删除用户角色
     * @param userId
     */
    void deleteByUserId(String userId);

    /**
     * 根据角色名称和用户id查询
     * @return
     */
    //List<UserRole> findDistinctByUserIdAndRoleId();

    /**
     * 判断是否存在
     * @param roleId
     * @param userId
     * @return
     */
    boolean existsByRoleIdAndUserId(String roleId,String userId);

    /**
     * 删除
     * @param userId
     * @param roleId
     */
    void deleteByUserIdAndRoleId(String userId,String roleId);

    /**
     * 获取用户拥有的所有权限
     * @return
     */
    List<UserRole> findByUserId(String userId);


}
