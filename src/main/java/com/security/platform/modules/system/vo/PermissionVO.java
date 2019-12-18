package com.security.platform.modules.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.security.platform.modules.system.entity.Permission;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 15:25
 * @description TODO
 */
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel(value = "PermissionVO对象", description = "PermissionVO对象")
public class PermissionVO extends Permission {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 父节点ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String parentId;

    /**
     * 子孙节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PermissionVO> children;

    /**
     * 上级菜单
     */
    private String parentName;

    /**
     * 菜单类型
     */
    private String typeName;

    /**
     * 按钮功能
     */
    private String buttonTypeName;

    /**
     * 是否新窗口打开
     */
    private String isOpenName;

}
