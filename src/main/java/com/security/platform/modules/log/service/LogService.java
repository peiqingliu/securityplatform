package com.security.platform.modules.log.service;

import com.security.platform.base.BaseService;
import com.security.platform.modules.log.entity.Log;
import com.security.platform.modules.system.entity.User;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 10:21
 * @description TODO
 */

public interface LogService extends BaseService<Log,String> {

    /**
     * 查询总的登录数
     * @param logType
     * @return
     */
    Integer countDistinctByLogType(Integer logType);

}
