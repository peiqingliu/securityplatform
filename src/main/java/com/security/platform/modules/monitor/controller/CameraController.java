package com.security.platform.modules.monitor.controller;

import com.security.platform.common.annotation.SystemLog;
import com.security.platform.common.enums.LogType;
import com.security.platform.common.utils.PageUtil;
import com.security.platform.common.utils.ResultUtil;
import com.security.platform.common.vo.CameraVo;
import com.security.platform.common.vo.PageVo;
import com.security.platform.common.vo.Result;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.devicesdk.service.AutoRegisterService;
import com.security.platform.modules.devicesdk.service.CapturePictureService;
import com.security.platform.modules.monitor.entity.Camera;
import com.security.platform.modules.monitor.service.CameraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.security.platform.common.constant.RedisKeyConstant.startService;
import static com.security.platform.common.constant.RedisKeyConstant.opened;
import static com.security.platform.common.constant.RedisKeyConstant.closed;


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
public class CameraController{

    @Autowired
    private CameraService cameraService;


    @Autowired
    private CapturePictureService capturePictureService;

    @Autowired
    private AutoRegisterService autoRegisterService;

    @Autowired
    private StringRedisTemplate redisTemplate;

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


    @ApiOperation(value = "检查服务是否开启")
    @GetMapping(value = "/checkServiceOpened")
    public Result<Object> checkServiceOpened(){
        if (StringUtils.isNotBlank(redisTemplate.opsForValue().get(startService))){
            return new ResultUtil<Object>().setData(opened,"服务已开启");
        }
        return new ResultUtil<Object>().setData(closed,"服务未开启");
    }

    @ApiOperation(value = "开启自动注册服务")
    @GetMapping(value = "/startAutoRegisterService")
    public Result<Object> startAutoRegisterService(){
        autoRegisterService.init();
        boolean isStartServer = autoRegisterService.startServer();
        if (isStartServer){
            redisTemplate.opsForValue().set(startService,opened);
            return new ResultUtil<Object>().setSuccessMsg("服务成功开启");
        }
        return new ResultUtil<Object>().setErrorMsg("服务开启失败");
    }

    @ApiOperation(value = "停止服务")
    @GetMapping(value = "/stopService")
    public Result<Object> stopService(){
        //删除缓存
        redisTemplate.delete(startService);
        //把所有的设备断开连接
        cameraService.getAll().forEach(camera -> {
            camera.setLoginHandle(0);
        });
        boolean stopServer = autoRegisterService.stopServer();
        if (stopServer){
            return new ResultUtil<Object>().setSuccessMsg("服务成功关闭");
        }
        return new ResultUtil<Object>().setErrorMsg("服务关闭失败");
    }

    @ApiOperation(value = "添加设备")
    @PostMapping(value = "/add")
    public Result<Object> add(@RequestBody Camera camera){
        //判断设备是否存在
        Camera camera1 = cameraService.findByDevcieId(camera.getDevcieId());
        if (null != camera1){
            return new ResultUtil<Object>().setErrorMsg("该设备已经添加");
        }
        cameraService.save(camera);
        //autoRegisterService.addDevice(camera.getDevcieId(),camera.getLoginName(),camera.getPassword());
        return new ResultUtil<Object>().setSuccessMsg("添加成功");
    }
    @ApiOperation(value = "更新设备")
    @PostMapping(value = "/update")
    public Result<Object> update(@RequestBody Camera camera){
        //判断设备是否存在
        cameraService.update(camera);
        return new ResultUtil<Object>().setSuccessMsg("更新成功");
    }

    /**
     * 删除
     */
    @SystemLog(description = "删除设备", type = LogType.OPERATION)
    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "设备id")
    public Result<Object> removeUser(@Valid String ids) {
        if (StringUtils.isNotEmpty(ids)){
            List<String> idList = Arrays.asList(ids.split(","));
            for(String id : idList){
                cameraService.delete(id);
            }
        }
        return new ResultUtil<Object>().setData("批量通过id删除数据成功");
    }

    @ApiOperation(value = "远程截图")
    @PostMapping(value = "/remoteCapture")
    public Result<Object> remoteCapture(@RequestBody Camera camera){
        boolean result =  autoRegisterService.capture(camera,0);
        if (result){
            return new ResultUtil<Object>().setSuccessMsg("截图成功");
        }
        return new ResultUtil<Object>().setErrorMsg("截图失败，请查看相关日志");
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

}
