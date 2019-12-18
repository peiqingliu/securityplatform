package com.security.platform.modules.system.dao;

import com.security.platform.base.BaseDao;
import com.security.platform.modules.system.entity.Permission;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/6 15:56
 * @description 权限数据处理层
 */
public interface PermissionDao extends BaseDao<Permission,String> {



    /**
     * 通过类型和状态获取
     * @param type
     * @param status
     * @return
     */
    List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);


}
