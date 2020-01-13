package com.security.platform.modules.monitor.controller;

import com.security.platform.base.BaseController;
import com.security.platform.common.utils.PageUtil;
import com.security.platform.common.utils.ResultUtil;
import com.security.platform.common.vo.CameraVo;
import com.security.platform.common.vo.PageVo;
import com.security.platform.common.vo.Result;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.deviceSDK.service.AutoRegisterService;
import com.security.platform.modules.deviceSDK.service.CapturePictureService;
import com.security.platform.modules.monitor.entity.Camera;
import com.security.platform.modules.monitor.service.CameraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


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

    @Autowired
    private AutoRegisterService autoRegisterService;

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

    @ApiOperation(value = "开启自动注册服务")
    @GetMapping(value = "/startAutoRegisterService")
    public Result<Object> startAutoRegisterService(){
        autoRegisterService.init();
        boolean isStartServer = autoRegisterService.startServer();
        if (isStartServer){
            return new ResultUtil<Object>().setSuccessMsg("服务成功开启");
        }
        return new ResultUtil<Object>().setErrorMsg("服务开启失败");
    }

    @ApiOperation(value = "添加设备")
    @PostMapping(value = "/add")
    public Result<Object> add(@RequestBody Camera camera){
        autoRegisterService.addDevice(camera.getDevcieId(),camera.getLoginName(),camera.getPassword());
        return new ResultUtil<Object>().setSuccessMsg("添加成功");
    }

    @ApiOperation(value = "远程截图")
    @PostMapping(value = "/remoteCapture")
    public Result<Object> remoteCapture(@RequestBody Camera camera){
        autoRegisterService.capture(camera,0);
        return new ResultUtil<Object>().setSuccessMsg("添加成功");
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
