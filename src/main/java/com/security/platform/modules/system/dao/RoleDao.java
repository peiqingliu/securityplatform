package com.security.platform.modules.system.dao;

import com.security.platform.base.BaseDao;
import com.security.platform.modules.system.entity.Role;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/6 15:49
 * @description 角色数据处理层
 */
public interface RoleDao extends BaseDao<Role,String> {

    Role findByRoleName(String roleName);

    List<Role> findAllByStatusEquals(Integer status);
}
