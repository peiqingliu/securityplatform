package com.security.platform.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.security.platform.base.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/6 11:24
 * @description 权限实体(菜单资源,按钮资源)
 */

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "t_permission")
@TableName("t_permission")
@ApiModel(value = "菜单权限")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单父主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "菜单父主键")
    private String parentId;

    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号")
    private String code;

    /**
     * 菜单标题
     */
    @ApiModelProperty(value = "菜单标题")
    private String title = this.getName();

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 菜单别名
     */
    @ApiModelProperty(value = "菜单别名")
    private String alias;

    /**
     * 请求地址
     */
    @ApiModelProperty(value = "请求地址")
    private String path;

    /**
     * 菜单资源
     */
    @ApiModelProperty(value = "图标")
    private String source;

    /**
     * 菜单类型
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "层级")
    private Integer level;


    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "按钮权限类型")
    private String buttonType;


    @ApiModelProperty(value = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    /**
     * 是否打开新页面
     */
    @ApiModelProperty(value = "是否打开新页面")
    private Integer isOpen;


    @ApiModelProperty(value = "说明备注")
    private String description;

}
