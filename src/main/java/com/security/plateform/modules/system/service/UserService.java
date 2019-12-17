package com.security.plateform.modules.system.service;

import com.security.plateform.base.BaseService;
import com.security.plateform.modules.system.entity.User;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/2 12:57
 * @description 用户接口
 */
public interface UserService extends BaseService<User,String> {

    User findByUsername(String username);
}
