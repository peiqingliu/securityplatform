package com.security.platform.modules.system.service;


import com.security.platform.base.BaseService;
import com.security.platform.modules.system.entity.Permission;
import com.security.platform.modules.system.entity.User;
import com.security.platform.modules.system.vo.PermissionVO;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/11 11:50
 * @description 权限接口
 */
public interface PermissionService extends BaseService<Permission,String> {

    /**
     * 通过类型和状态获取
     * @param type
     * @param status
     * @return
     */
    List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);

    List<PermissionVO> routes(List<String> roleIds);
}
