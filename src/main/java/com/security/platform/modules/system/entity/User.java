package com.security.platform.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.security.platform.base.BaseEntity;
import com.security.platform.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/6 9:29
 * @description 账号实体
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "t_user")
@TableName("t_user")
@ApiModel(value = "账号")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "账号")
    @Column(unique = true, nullable = false)
    private String username;

    @ApiModelProperty(value = "姓名")
    private String fullname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "省市县地址")
    private String address;

    @ApiModelProperty(value = "街道地址")
    private String street;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "密码强度")
    @Column(length = 2)
    private String passStrength;

    @ApiModelProperty(value = "用户头像")
    @Column(length = 1000)
    private String avatar = CommonConstant.USER_DEFAULT_AVATAR;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type = CommonConstant.USER_TYPE_NORMAL;

    @ApiModelProperty(value = "状态 默认0正常 -1拉黑")
    private Integer status = CommonConstant.USER_STATUS_NORMAL;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;

    @ApiModelProperty(value = "排序")
    private Integer sortOrder;


    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有角色")
    private List<Role> roles =  new ArrayList<>(16);

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有角色Ids")
    private List<String> roleIds = new ArrayList<>(16);

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有的权限")
    private List<Permission> permissions = new ArrayList<>(16);

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "创建用户时候用到")
    private String defaultRole;


}
