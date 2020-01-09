package com.security.platform.modules.common.controller;

import com.security.platform.common.utils.ResultUtil;
import com.security.platform.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/10 14:21
 * @description springSecurity 相关的接口
 */
@Slf4j
@RestController
@Api(description = "Security相关接口")
@Transactional
@RequestMapping("/us/common")
public class SecurityController {

    @GetMapping(value = "/needLogin")
    @ApiOperation(value = "没有登录")
    public Result<Object> needLogin(){
        return new ResultUtil<Object>().setErrorMsg(401,"您还未登录");
    }
}
