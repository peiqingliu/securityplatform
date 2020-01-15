package com.security.platform.modules.api;

import com.security.platform.common.annotation.SystemLog;
import com.security.platform.common.enums.LogType;
import com.security.platform.common.utils.*;
import com.security.platform.common.vo.CameraVo;
import com.security.platform.common.vo.Result;
import com.security.platform.common.vo.VerificationCodeParam;
import com.security.platform.modules.devicesdk.service.CapturePictureService;
import com.security.platform.netsdk.module.LoginModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.security.platform.common.constant.RedisKeyConstant.pictureUrlKey;


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
    private RedisUtil redisUtil;


    @SystemLog(description = "远程抓图", type = LogType.OPERATION)
    @PostMapping("/remoteCapture")
    public Result<Object> remoteCapture(@ModelAttribute VerificationCodeParam param,
                                        @ModelAttribute CameraVo cameraVo){
        // 验证信息是否被篡改
        if(!SignUtil.validateMessage(param, ticketSecret)) {
            return new ResultUtil<Object>().setErrorMsg("签名信息错误!");
        }
        // 验证时间戳,防止重复提交
        Boolean validateResult = timestampUtil.validateTimestamp("verificationCode", param.getTimeStamp());
        if(!validateResult) {
            return new ResultUtil<Object>().setErrorMsg("提交太频繁，请稍后重试!");
        }

        boolean init = capturePictureService.init();
        if (!init){
            return new ResultUtil<Object>().setErrorMsg("Initialize SDK failed");
        }
        boolean login = capturePictureService.login(cameraVo.getIp(),cameraVo.getPort(),cameraVo.getLoginName(),cameraVo.getPassword());
        if (!login){
            return new ResultUtil<Object>().setErrorMsg("LOGIN_FAILED" + ", " + LoginModule.netsdk.CLIENT_GetLastError() + ",ERROR_MESSAGE" + 0);
        }
        //远程截图
        capturePictureService.remoteCapture();
        String pictureUrl;
        while (true){
            pictureUrl = redisUtil.getPictureUrl(cameraVo.getIp() + ":" + pictureUrlKey);
            if (StringUtils.isNotBlank(pictureUrl)){
                break;
            }
        }
        return new ResultUtil<Object>().setData(pictureUrl);
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


    @ApiOperation("获取签名信息")
    @GetMapping("/getSign")
    public Result<String> getSign(@RequestParam("timeStamp") long currentTime){
        CheckUtil check = new CheckUtil(ticketSecret);
        check.setValue("timeStamp",currentTime);
        String sign = check.makeSign();
        log.info("sign ===" +sign);
        return new ResultUtil<String>().setData(sign);
    }


}
