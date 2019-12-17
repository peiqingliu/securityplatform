package com.security.plateform.modules.system.dao;

import com.security.plateform.base.BaseDao;
import com.security.plateform.modules.system.entity.User;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/2 12:56
 * @description 用户数据处理层
 */
public interface UserDao extends BaseDao<User,String> {

    /**
     * 通过账号获取用户
     * @param username
     * @return
     */
    List<User> findByUsername(String username);
}
