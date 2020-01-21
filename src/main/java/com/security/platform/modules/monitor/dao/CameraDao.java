package com.security.platform.modules.monitor.dao;

import com.security.platform.base.BaseDao;
import com.security.platform.modules.monitor.entity.Camera;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/20 11:30
 * @description TODO
 */
public interface CameraDao extends BaseDao<Camera,String> {


    Camera findByDevcieId(String devcieId);

    /**
     * 根据组别进行查找
     * @param groupId
     * @return
     */
    List<Camera> findAllByGroupId(String groupId);

    /**
     * 根据登录句柄查询
     * @param loginHandle
     * @return
     */
    Camera  findByLoginHandle(long loginHandle);

    long countDistinctByLoginHandleNot(long loginHandle);

}
