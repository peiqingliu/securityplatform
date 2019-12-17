package com.security.plateform.modules.system.service;

import com.security.plateform.base.BaseService;
import com.security.plateform.modules.system.entity.RolePermission;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/17 14:50
 * @description TODO
 */
public interface RolePermissionService extends BaseService<RolePermission,String> {

    List<RolePermission> findAllByRoleId(String roleId);
}
