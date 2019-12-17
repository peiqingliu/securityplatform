package com.security.plateform.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.security.plateform.modules.system.dao.UserDao;
import com.security.plateform.modules.system.entity.*;
import com.security.plateform.modules.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/3 14:10
 * @description 用户接口实现
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDao getRepository() {
        return userDao;
    }

    @Override
    public User findByUsername(String account) {
        List<User> list = userDao.findByUsername(account);
        if (list.size()>0 && list != null){
            User user = list.get(0);
            //关联角色
            List<Role> roles = new ArrayList<>(16);
            List<RolePermission> rolePermissionList = new ArrayList<>(16);
            List<UserRole> userRoles = userRoleService.findAllByUserId(user.getId());
            userRoles.forEach(userRole -> {
                Role role = roleService.get(userRole.getRoleId());
                List<RolePermission> rolePermissions = rolePermissionService.findAllByRoleId(role.getId());
                rolePermissionList.addAll(rolePermissions);
                roles.add(role);
            });
            log.info("roles" + roles);
            List<String> roleIds = new ArrayList<>();
            roles.forEach( r -> {
                roleIds.add(r.getId());
                log.info(r.getRoleName() + r.getRoleAlias());
            });
            user.setRoleIds(roleIds);
            user.setRoles(roles);
            // 关联权限菜单
            List<Permission> permissions = new ArrayList<>(16);
            rolePermissionList.forEach(rolePermission -> {
                Permission permission = permissionService.get(rolePermission.getPermissionId());
                permissions.add(permission);
            });
            user.setPermissions(permissions);
            return user;
        }
        return null;
    }
}
