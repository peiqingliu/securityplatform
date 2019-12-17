package com.security.plateform.modules.system.dao;

import com.security.plateform.base.BaseDao;
import com.security.plateform.modules.system.entity.RolePermission;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/3 14:47
 * @description 角色权限数据处理层
 */
public interface RolePermissionDao extends BaseDao<RolePermission,String> {

    List<RolePermission> findAllByRoleId(String roleId);
}
