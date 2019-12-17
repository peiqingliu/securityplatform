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
 * @date 2019/9/6 13:47
 * @description 用户角色关系表
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "t_user_role")
@ApiModel(value = "用户角色关系表")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户唯一id")
    private String userId;

    @ApiModelProperty(value = "角色唯一id")
    private String roleId;

}
