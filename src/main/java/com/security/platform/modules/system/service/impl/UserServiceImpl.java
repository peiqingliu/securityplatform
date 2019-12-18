package com.security.platform.modules.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.security.platform.common.utils.SecurityUtil;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.system.dao.*;
import com.security.platform.modules.system.entity.*;
import com.security.platform.modules.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 11:09
 * @description TODO
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private PermissionDao permissionDao;


    @Override
    public Page<User> findByCondition(User user, SearchVo searchVo, Pageable pageable) {

        return userDao.findAll(new Specification<User>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> usernameFiled = root.get("username");
                Path<Integer> statusField = root.get("status");
                Path<Date> createTimeField = root.get("createTime");
                List<Predicate> list = new ArrayList<Predicate>();


                //账号
                if (StrUtil.isNotBlank(user.getUsername())){
                    list.add(cb.like(usernameFiled,'%' + user.getUsername() + '%'));
                }


                //状态
                if (null != user.getStatus()){
                    list.add(cb.equal(statusField,user.getStatus()));
                }

                //创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField,start,DateUtil.endOfDay(end)));
                }

                //数据权限

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        },pageable);
    }

    @Override
    public User findByUsername(String username) {
        List<User> list = userDao.findByUsername(username);
        if (list.size()>0 && list != null){
            User user = list.get(0);
            //关联部门
            //关联角色
            List<Role> roles = new ArrayList<>(16);
            List<RolePermission> rolePermissions = new ArrayList<>(16);
            List<UserRole> userRoles = userRoleDao.findByUserId(user.getId());
            userRoles.forEach(userRole -> {
                String userRoleId = userRole.getRoleId();
                Role role = roleDao.getOne(userRoleId);
                List<RolePermission> rolePermissionList = rolePermissionDao.findByRoleId(userRoleId);
                rolePermissions.addAll(rolePermissionList);
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
            rolePermissions.forEach(rolePermission -> {
                Permission permission = permissionDao.getOne(rolePermission.getPermissionId());
                permissions.add(permission);
            });

            user.setPermissions(permissions);
            log.info("roles" + permissions);
            return user;
        }
        return null;
    }



    @Override
    public UserDao getRepository() {
        return userDao;
    }
}
