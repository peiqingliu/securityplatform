package com.security.plateform.modules.system.controller;

import com.security.plateform.base.BaseController;
import com.security.plateform.modules.system.entity.User;
import com.security.plateform.modules.system.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/2 12:55
 * @description TODO
 */

@Slf4j
@RestController
@Api(description = "用户接口")
@RequestMapping("/system/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserController extends BaseController<User,String> {


    @Autowired
    private UserService userService;

    @Override
    public UserService getService() {
        return userService;
    }
}
