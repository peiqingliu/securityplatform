package com.security.platform.modules.monitor.service;

import com.security.platform.base.BaseService;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.monitor.entity.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Camera findByDevcieId(String devcieId);

    Long count();

    long countByLoginHandle(long loginHandle);


    long countByLoginHandleNot(long loginHandle);
}
