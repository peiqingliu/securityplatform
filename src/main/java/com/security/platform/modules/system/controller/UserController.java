package com.security.platform.modules.system.controller;

import cn.hutool.core.util.StrUtil;
import com.security.platform.base.BaseController;
import com.security.platform.common.utils.PageUtil;
import com.security.platform.common.utils.ResultUtil;
import com.security.platform.common.vo.PageVo;
import com.security.platform.common.vo.Result;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.system.entity.Role;
import com.security.platform.modules.system.entity.User;
import com.security.platform.modules.system.entity.UserRole;
import com.security.platform.modules.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/9 13:43
 * @description 用户账号控制层
 */

@Slf4j
@RestController
@Api(description = "用户接口")
@RequestMapping("/cetc/system/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserController extends BaseController<User,String> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @GetMapping(value = "/getByCondition")
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<Page<User>> getByCondition(@ModelAttribute User user,
                                             @ModelAttribute SearchVo searchVo,
                                             @ModelAttribute PageVo pageVo){
        pageVo.setSort("sortOrder");
        pageVo.setOrder("asc");
        Page<User> page =  userService.findByCondition(user,searchVo, PageUtil.initPage(pageVo));
        //关联部门
        for (User u : page.getContent()){
            // 关联部门
            //关联角色
            List<Role> roles = findRoleByUserId(u.getId());
            u.setRoles(roles);

            // 清除持久上下文环境 避免后面语句导致持久化
            //entityManager.clear();
            u.setPassword(null);
        }
        return new ResultUtil<Page<User>>().setData(page);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/admin/add")
    public Result<Object> register(@Valid @RequestBody User u){

        if(StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())){
            return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
        }

        if(userService.findByUsername(u.getUsername())!=null){
            return new ResultUtil<Object>().setErrorMsg("该账号已被注册");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        User user = userService.save(u);
        if(user==null){
            return new ResultUtil<Object>().setErrorMsg("添加失败");
        }
        return new ResultUtil<Object>().setSuccessMsg("添加成功");
    }

    private List<Role> findRoleByUserId(String userId){
        List<UserRole> userRoles = userRoleService.findByUserId(userId);
        List<Role> roles = new ArrayList<>(16);
        for (UserRole userRole : userRoles){
            Role role = roleService.get(userRole.getRoleId());
            roles.add(role);
        }
        return roles;
    }


    @Override
    public UserService getService() {
        return userService;
    }
}
