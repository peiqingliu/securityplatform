package com.security.platform.modules.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.security.platform.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/19 11:24
 * @description TODO
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "t_camera")
@TableName("t_camera")
@ApiModel(value = "摄像头")
public class Camera extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @Column(unique = true, nullable = false)
    private String cameraName;


    @ApiModelProperty(value = "编码")
    @Column(unique = true, nullable = false)
    private String cameraCode;

    @ApiModelProperty(value = "型号")
    @Column(unique = true, nullable = false)
    private String cameraModel;

    @ApiModelProperty(value = "登录密码")
    @Column(unique = true, nullable = false)
    private String password;

    @ApiModelProperty(value = "安装位置")
    private String position;

    @ApiModelProperty(value = "摄像头图片")
    private String picture;

    @ApiModelProperty("设备ip")
    private String ip;

    @ApiModelProperty("负责人电话")
    private String mobile;

    @ApiModelProperty("排序")
    private Integer sortOrder;

    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("描述")
    private String description;
}
