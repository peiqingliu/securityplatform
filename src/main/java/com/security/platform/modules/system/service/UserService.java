package com.security.platform.modules.system.service;

import com.security.platform.base.BaseService;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/6 11:58
 * @description 用户接口
 */

public interface UserService extends BaseService<User,String> {


    /**
     * 多条件分页获取用户
     * @param user
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<User> findByCondition(User user, SearchVo searchVo, Pageable pageable);

    /**
     * 通过账号获取用户
     * @param username
     * @return
     */
    User findByUsername(String username);


}
