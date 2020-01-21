package com.security.platform.modules.monitor.service;

import com.security.platform.base.BaseService;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.monitor.entity.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/20 11:31
 * @description 摄像头接口
 */
public interface CameraService extends BaseService<Camera,String> {

    /**
     * 多条件分页获取
     * @param camera
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<Camera> findByCondition(Camera camera, SearchVo searchVo, Pageable pageable);

    /**
     *  根据设备id 查找
     * @param devcieId
     * @return
     */
    Camera findByDevcieId(String devcieId);

    /**
     * 根据登录句柄查询
     * @param loginHandle
     * @return
     */
    Camera findByLoginHandle(Long loginHandle);

    /**
     * 根据组别进行查找
     * @return
     */
    List<Camera> findByGroupId(String groupId);

    /**
     * 总数
     * @return
     */
    Long count();


    /**
     * 登录数量统计
     * @param loginHandle
     * @return
     */
    long countByLoginHandleNot(long loginHandle);
}
