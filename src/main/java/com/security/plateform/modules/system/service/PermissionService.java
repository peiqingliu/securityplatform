package com.security.plateform.modules.system.service;

import com.security.plateform.base.BaseService;
import com.security.plateform.modules.system.entity.Permission;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/4 14:49
 * @description 权限接口
 */
public interface PermissionService  extends BaseService<Permission,String> {

    /**
     * 通过类型和状态获取
     * @param type
     * @param status
     * @return
     */
    List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);
}
