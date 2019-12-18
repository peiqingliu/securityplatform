package com.security.platform.modules.log.service.impl;

import com.security.platform.base.BaseDao;
import com.security.platform.modules.log.dao.LogDao;
import com.security.platform.modules.log.entity.Log;
import com.security.platform.modules.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 10:21
 * @description TODO
 */
@Slf4j
@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public LogDao getRepository() {
        return logDao;
    }
}
