package com.security.plateform.modules.system.service.impl;

import com.security.plateform.modules.system.dao.PermissionDao;
import com.security.plateform.modules.system.entity.Permission;
import com.security.plateform.modules.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/17 14:54
 * @description TODO
 */

@Slf4j
@Service
@Transactional
public class PermissionServiceImpl  implements PermissionService {


    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PermissionDao getRepository() {
        return permissionDao;
    }

    @Override
    public List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status) {
        return permissionDao.findByTypeAndStatusOrderBySortOrder(type,status);
    }
}
