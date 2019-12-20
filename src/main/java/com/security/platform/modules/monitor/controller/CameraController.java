package com.security.platform.modules.monitor.controller;

import com.security.platform.base.BaseController;
import com.security.platform.modules.monitor.entity.Camera;
import com.security.platform.modules.monitor.service.CameraService;
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
 * @date 2019/12/20 13:45
 * @description TODO
 */
@Slf4j
@RestController
@Api(description = "摄像头端点")
@RequestMapping("/cetc/monitor/camera")
@CacheConfig(cacheNames = "camera")
@Transactional
public class CameraController extends BaseController<Camera,String> {

    @Autowired
    private CameraService cameraService;

    @Override
    public CameraService getService() {
        return cameraService;
    }
}
