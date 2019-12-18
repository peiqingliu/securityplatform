package com.security.platform.modules.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.security.platform.common.tool.INode;
import com.security.platform.modules.system.entity.Permission;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/17 13:49
 * @description 前端菜单视图实体
 */
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel(value = "MenuVO对象", description = "MenuVO对象")
public class MenuVO extends Permission implements INode {

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
    private List<INode> children;

    @Override
    public List<INode> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

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
