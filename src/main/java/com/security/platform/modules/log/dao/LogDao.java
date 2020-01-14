package com.security.platform.modules.log.dao;

import com.security.platform.base.BaseDao;
import com.security.platform.modules.log.entity.Log;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/2 10:40
 * @description TODO
 */
public interface LogDao extends BaseDao<Log,String> {

    /**
     * 根据用户查询日志
     * @param username
     * @return
     */
    List<Log> findAllByUsernameOrderByCreateTimeDesc(String username);

    /**
     * 查询一段时间登录数
     * @return
     */
    Integer countDistinctByLogTypeIsAndCreateTimeBetween(Integer logType, Date startTime, Date endTime);

    /**
     * 查询总的登录数
     * @param logType
     * @return
     */
    Integer countDistinctByLogType(Integer logType);
}
