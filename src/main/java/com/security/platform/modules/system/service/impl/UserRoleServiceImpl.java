package com.security.platform.modules.system.service.impl;

import com.security.platform.base.BaseDao;
import com.security.platform.modules.system.dao.UserRoleDao;
import com.security.platform.modules.system.entity.User;
import com.security.platform.modules.system.entity.UserRole;
import com.security.platform.modules.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 16:53
 * @description TODO
 */
@Slf4j
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<UserRole> findByRoleId(String roleId) {
        return userRoleDao.findByRoleId(roleId);
    }

    @Override
    public List<UserRole> findByUserId(String userId) {
        return userRoleDao.findByUserId(userId);
    }


    @Override
    public UserRoleDao getRepository() {
        return userRoleDao;
    }
}
