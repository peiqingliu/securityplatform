package com.security.platform.modules.api;

import com.security.platform.common.annotation.SystemLog;
import com.security.platform.common.enums.LogType;
import com.security.platform.common.utils.*;
import com.security.platform.common.vo.CameraVo;
import com.security.platform.common.vo.Result;
import com.security.platform.common.vo.VerificationCodeParam;
import com.security.platform.modules.api.dto.ApiDataDto;
import com.security.platform.modules.devicesdk.service.AutoRegisterService;
import com.security.platform.modules.devicesdk.service.CapturePictureService;
import com.security.platform.modules.monitor.entity.Camera;
import com.security.platform.modules.monitor.service.CameraService;
import com.security.platform.netsdk.module.LoginModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/9 10:59
 * @File ApiController
 * @Software IntelliJ IDEA
 * @description 第三方接口
 */

@Slf4j
@RestController
@Api(description = "第三方接口")
@RequestMapping("/us/api/")
@Transactional
public class ApiController {

    @Value("${us.ticketSecret}")
    private String ticketSecret;


    @Autowired
    private TimestampUtil timestampUtil;


    @Autowired
    private CapturePictureService capturePictureService;

    @Autowired
    private AutoRegisterService autoRegisterService;



    @Autowired
    private CameraService cameraService;


    /**
     * 为了能够使 接口返回抓图的结果，此处使用redis在不停的轮训结果
     * @param param
     * @param groupId 组别
     * @return
     */
    @SystemLog(description = "远程抓图", type = LogType.OPERATION)
    @GetMapping("/remoteCapture")
    public Result<List<ApiDataDto>> remoteCapture(@ModelAttribute VerificationCodeParam param,
                                        @RequestParam("groupId") String groupId){
        // 验证信息是否被篡改
        if(!SignUtil.validateMessage(param, ticketSecret)) {
            return new ResultUtil<List<ApiDataDto>>().setErrorMsg("签名信息错误!");
        }
        // 验证时间戳,防止重复提交
        Boolean validateResult = timestampUtil.validateTimestamp("verificationCode", param.getTimeStamp());
        if(!validateResult) {
            return new ResultUtil<List<ApiDataDto>>().setErrorMsg("提交太频繁，请稍后重试!");
        }

        List<ApiDataDto> result = autoRegisterService.captureByGroup(groupId);
        return new ResultUtil<List<ApiDataDto>>().setData(result);
    }

    //签名校验
    @PostMapping(value = "/valid")
    public boolean signValid(@ModelAttribute VerificationCodeParam param) {
        // 验证信息是否被篡改
        if(!SignUtil.validateMessage(param, ticketSecret)) {
            return false;
        }
        // 验证时间戳,防止重复提交
        Boolean validateResult = timestampUtil.validateTimestamp("verificationCode", param.getTimeStamp());
        if(!validateResult) {
            return false;
        }
        return true;
    }

    @GetMapping("/getByloginHandle")
    public Result<Camera> getByLoginHandle(@RequestParam("loginHandler") long loginHandler){
        Camera camera =  cameraService.findByLoginHandle(loginHandler);
        return new ResultUtil<Camera>().setData(camera);
    }


    @ApiOperation("获取签名信息")
    @GetMapping("/getSign")
    public Result<String> getSign(@RequestParam("timeStamp") long currentTime){
        CheckUtil check = new CheckUtil(ticketSecret);
        check.setValue("timeStamp",currentTime);
        String sign = check.makeSign();
        log.info("sign ===" +sign);
        return new ResultUtil<String>().setData(sign);
    }

    @ApiOperation(value = "二维码远程截图")
    @GetMapping("/qrCodeRemoteCapture")
    public String qrCodeRemoteCapture(@RequestParam("id") String id){
        log.info("使用二维码进行截图");
        Camera camera = cameraService.get(id);
        boolean result =  autoRegisterService.capture(camera,0);
        if (result){
            return "success Capture";
        }
        return "fail Capture";
    }


}
