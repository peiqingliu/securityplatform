package com.security.platform.modules.log.controller;

import com.security.platform.modules.log.service.LogService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/2 10:44
 * @description TODO
 */

@RestController
@AllArgsConstructor
@RequestMapping("/cetc/log")
@Api(value = "日志通用接口", tags = "日志通用接口")
public class LogController {


    @Autowired
    private LogService logService;


}
