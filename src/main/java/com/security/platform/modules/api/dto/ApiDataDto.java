package com.security.platform.modules.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/20 16:01
 * @File ApiDataDto
 * @Software IntelliJ IDEA
 * @description todo
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Data
@ToString
public class ApiDataDto {

    @ApiModelProperty(value = "设备Id")
    private String devcieId;

    @ApiModelProperty(value = "截图URL")
    private String pictureUrl;

    @ApiModelProperty(value = "描述")
    private String description;
}
