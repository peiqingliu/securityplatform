package com.security.platform.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.security.platform.base.BaseEntity;
import com.security.platform.common.constant.CommonConstant;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/6 11:16
 * @description 角色实体
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "t_role")
@TableName("t_role")
@ApiModel(value = "角色")
public class Role extends BaseEntity {
    private static final long serialVersionUID = -8794838294650581528L;


    /**
     * 父主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "父主键")
    private String parentId;


    /**
     * 祖级机构主键
     */
    @ApiModelProperty(value = "祖级机构主键")
    private String ancestors;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名,需以ROLE_开头")
    private String roleName;

    /**
     * 角色别名
     */
    @ApiModelProperty(value = "角色别名(中文名称)")
    private String roleAlias;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sortOrder;

    /**
     * 是否为默认角色
     */
    @ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;


    /**
     * 数据权限类型
     */
    @ApiModelProperty(value = "数据权限类型 0全部默认 1自定义")
    private Integer dataType = CommonConstant.DATA_TYPE_ALL;

    /**
     * 角色类型
     */
    @ApiModelProperty(value = "数据权限类型 0全部默认 1自定义")
    private Integer roleType;

    /**
     * 描述信息
     */
    @ApiModelProperty(value = "备注")
    private String description;



    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "角色名称")
    private String name;

}
