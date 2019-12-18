package com.security.platform.modules.system.controller;

import com.security.platform.base.BaseController;
import com.security.platform.base.BaseService;
import com.security.platform.common.utils.ResultUtil;
import com.security.platform.common.utils.SecurityUtil;
import com.security.platform.common.vo.Result;
import com.security.platform.modules.system.entity.Permission;
import com.security.platform.modules.system.entity.User;
import com.security.platform.modules.system.service.PermissionService;
import com.security.platform.modules.system.vo.MenuVO;
import com.security.platform.modules.system.vo.PermissionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 14:56
 * @description TODO
 */
@Slf4j
@RestController
@Api(description = "菜单/权限管理接口")
@RequestMapping("/cetc/system/permission")
@CacheConfig(cacheNames = "permission")
@Transactional
public class PermissionController extends BaseController<Permission,String> {


    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 前端菜单数据
     */
    @GetMapping("/routes")
    @ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
    public Result<List<PermissionVO>> routes(User user) {
        List<String> roleIds = securityUtil.getRoleIds();
        List<PermissionVO> list = permissionService.routes((user == null) ? null : roleIds);
        return new ResultUtil<List<PermissionVO>>().setData(list);
    }

    @Override
    public PermissionService getService() {
        return permissionService;
    }
}
