package com.security.plateform.modules.system.service.impl;

import com.security.plateform.modules.system.dao.RoleDao;
import com.security.plateform.modules.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/4 14:52
 * @description TODO
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public RoleDao getRepository() {
        return roleDao;
    }
}
