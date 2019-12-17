package com.security.plateform.modules.system.entity;

import com.security.plateform.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/6 14:16
 * @description 角色与权限关联关系表
 */

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Table(name = "t_role_permission")
@Entity
@ApiModel(value = "角色权限关联表")
public class RolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "权限id")
    private String permissionId;
}
