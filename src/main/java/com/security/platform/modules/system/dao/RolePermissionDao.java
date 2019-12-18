package com.security.platform.modules.system.dao;

import com.security.platform.base.BaseDao;
import com.security.platform.modules.system.entity.RolePermission;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/25 13:37
 * @description 角色权限数据处理层
 */
public interface RolePermissionDao extends BaseDao<RolePermission,String> {

    /**
     * 通过permissionId获取
     * @param permissionId
     * @return
     */
    List<RolePermission> findByPermissionId(String permissionId);

    /**
     * 通过roleId获取
     * @param roleId
     */
    List<RolePermission> findByRoleId(String roleId);

    /**
     * 根据角色id查询
     * @param roleIds
     * @return
     */
    List<RolePermission> findByRoleIdIn(List<String> roleIds);

    /**
     * 通过roleId删除
     * @param roleId
     */
    void deleteByRoleId(String roleId);

    /**
     * 根据角色ids进行循环删除
     * @param roleIds
     */
    void deleteByRoleIdIn(List<String> roleIds);
}
