package com.security.platform.modules.system.service.impl;

import com.security.platform.base.BaseDao;
import com.security.platform.modules.system.dao.RoleDao;
import com.security.platform.modules.system.entity.Role;
import com.security.platform.modules.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 11:13
 * @description TODO
 */

@Slf4j
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public RoleDao getRepository() {
        return roleDao;
    }
}
