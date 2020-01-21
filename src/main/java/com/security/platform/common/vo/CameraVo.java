package com.security.platform.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/9 15:35
 * @File CameraVo
 * @Software IntelliJ IDEA
 * @description todo
 */
@Slf4j
@Data
@ToString
@ApiModel(value = "摄像头信息")
public class CameraVo {

    @ApiModelProperty(value = "摄像头ip")
    private String ip;
    @ApiModelProperty(value = "摄像头port")
    private int port;
    @ApiModelProperty(value = "摄像头loginName")
    private String loginName;
    @ApiModelProperty(value = "摄像头password")
    private String password;

}
