package com.security.plateform.modules.system.service;

import com.security.plateform.base.BaseService;
import com.security.plateform.modules.system.entity.UserRole;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/4 14:50
 * @description 用户角色接口
 */
public interface UserRoleService extends BaseService<UserRole,String> {

    List<UserRole> findAllByUserId(String userId);
}
