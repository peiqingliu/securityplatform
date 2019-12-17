package com.security.plateform.modules.system.dao;

import com.security.plateform.base.BaseDao;
import com.security.plateform.modules.system.entity.UserRole;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/4 14:48
 * @description 人员角色关系数据处理层
 */
public interface UserRoleDao extends BaseDao<UserRole,String> {

    /**
     * 根据用户id
     * @param userId
     * @return
     */
    List<UserRole> findAllByUserId(String userId);
}
