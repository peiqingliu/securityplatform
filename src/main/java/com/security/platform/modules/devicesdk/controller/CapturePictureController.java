package com.security.platform.modules.devicesdk.controller;

import com.security.platform.common.vo.CameraVo;
import com.security.platform.modules.devicesdk.service.CapturePictureService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/7 11:39
 * @File CapturePictureController
 * @Software IntelliJ IDEA
 * @description todo
 */
@Slf4j
@RestController
@Api(description = "测试端点")
@RequestMapping("/us/test/capturePicture")
@Transactional
public class CapturePictureController {

    @Autowired
    private CapturePictureService capturePictureService;

    @GetMapping("/handleCapturePicture")
    public String loginDevice(CameraVo cameraVo){
        capturePictureService.handleCapturePicture();
        return "success";
    }

    @GetMapping("/logout")
    public String logoutDevice(){
        capturePictureService.logout();
        return "success";
    }


}
