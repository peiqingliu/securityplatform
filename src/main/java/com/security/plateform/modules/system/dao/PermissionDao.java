package com.security.plateform.modules.system.dao;

import com.security.plateform.base.BaseDao;
import com.security.plateform.modules.system.entity.Permission;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/17 14:47
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
