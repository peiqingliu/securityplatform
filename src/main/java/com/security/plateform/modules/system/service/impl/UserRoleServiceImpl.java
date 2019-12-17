package com.security.plateform.modules.system.service.impl;

import com.security.plateform.modules.system.dao.UserRoleDao;
import com.security.plateform.modules.system.entity.UserRole;
import com.security.plateform.modules.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/4 14:51
 * @description TODO
 */
@Slf4j
@Service
@Transactional
public class UserRoleServiceImpl  implements UserRoleService {


    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRoleDao getRepository() {
        return userRoleDao;
    }

    @Override
    public List<UserRole> findAllByUserId(String userId) {
        return userRoleDao.findAllByUserId(userId);
    }
}
