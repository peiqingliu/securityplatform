package com.security.platform.modules.async.controller;

import com.security.platform.common.utils.ResultUtil;
import com.security.platform.common.vo.Result;
import com.security.platform.modules.async.dto.WelDataDto;
import com.security.platform.modules.async.task.WelFutureTask;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/14 10:40
 * @File WelController
 * @Software IntelliJ IDEA
 * @description 首页数据展示
 */
@RestController
@RequestMapping("/us/system")
public class WelController {

    @Autowired
    private WelFutureTask welFutureTask;

    @ApiOperation("获取签名信息")
    @GetMapping("/welStatistics")
    public Result<WelDataDto> welStatistics(){
        WelDataDto dataDto = welFutureTask.getWelDataStatistics();
        return new ResultUtil<WelDataDto>().setData(dataDto);
    }
}
