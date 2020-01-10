package com.security.platform.modules.monitor.controller;

import com.security.platform.base.BaseController;
import com.security.platform.common.utils.PageUtil;
import com.security.platform.common.utils.ResultUtil;
import com.security.platform.common.vo.CameraVo;
import com.security.platform.common.vo.PageVo;
import com.security.platform.common.vo.Result;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.deviceSDK.service.CapturePictureService;
import com.security.platform.modules.monitor.entity.Camera;
import com.security.platform.modules.monitor.service.CameraService;
import com.security.platform.netsdk.demo.LoginModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/us/monitor/camera")
@CacheConfig(cacheNames = "camera")
@Transactional
public class CameraController extends BaseController<Camera,String> {

    @Autowired
    private CameraService cameraService;


    @Autowired
    private CapturePictureService capturePictureService;


    @Transactional
    @GetMapping(value = "/getByCondition")
    @ApiOperation(value = "多条件分页获取列表")
    public Result<Page<Camera>> getByCondition(@ModelAttribute Camera camera,
                                             @ModelAttribute SearchVo searchVo,
                                             @ModelAttribute PageVo pageVo){
        pageVo.setSort("createTime");
        pageVo.setOrder("asc");
        Page<Camera> page =  cameraService.findByCondition(camera,searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Camera>>().setData(page);
    }


    @ApiOperation("查询设备是否在线")
    @GetMapping("/isOnline")
    public Result<Object> isOnline( @ModelAttribute CameraVo cameraVo){
        boolean result = capturePictureService.isOnline(cameraVo);
        if (result){

            return new ResultUtil<Object>().setSuccessMsg("设备在线");
        }
        return new ResultUtil<Object>().setErrorMsg("设备不在线");
    }


    @Override
    public CameraService getService() {
        return cameraService;
    }
}
