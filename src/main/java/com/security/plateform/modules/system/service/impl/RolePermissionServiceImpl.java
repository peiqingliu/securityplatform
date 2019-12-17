package com.security.plateform.modules.system.service.impl;

import com.security.plateform.modules.system.dao.RolePermissionDao;
import com.security.plateform.modules.system.entity.RolePermission;
import com.security.plateform.modules.system.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/17 14:53
 * @description TODO
 */
@Slf4j
@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public RolePermissionDao getRepository() {
        return rolePermissionDao;
    }

    @Override
    public List<RolePermission> findAllByRoleId(String roleId) {
        return rolePermissionDao.findAllByRoleId(roleId);
    }
}
