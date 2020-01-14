package com.security.platform.modules.async.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/14 10:41
 * @File WelDataDto
 * @Software IntelliJ IDEA
 * @description 首页数据
 */
@Slf4j
@Data
@ToString
public class WelDataDto {

    @ApiModelProperty(value = "设备总数")
    private long deviceTotal;

    @ApiModelProperty(value = "设备在线总数")
    private long deviceOnlineTotal;

    @ApiModelProperty(value = "接口被调用总数")
    private int interfaceUseTotal;

    @ApiModelProperty(value = "登录人数")
    private int loginTotal;

}
