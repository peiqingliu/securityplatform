package com.security.platform.modules.system.service.impl;

import com.security.platform.common.utils.TreeToolUtils;
import com.security.platform.modules.system.dao.PermissionDao;
import com.security.platform.modules.system.dao.RolePermissionDao;
import com.security.platform.modules.system.entity.Permission;
import com.security.platform.modules.system.entity.Role;
import com.security.platform.modules.system.entity.RolePermission;
import com.security.platform.modules.system.service.PermissionService;
import com.security.platform.modules.system.vo.PermissionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 13:12
 * @description TODO
 */
@Slf4j
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public PermissionDao getRepository() {
        return permissionDao;
    }


    @Override
    public List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status) {
        return permissionDao.findByTypeAndStatusOrderBySortOrder(type,status);
    }

    @Override
    public List<PermissionVO> routes(List<String> roleIds) {
        if (roleIds.size() > 0){
            List<Permission> permissions = findAllPermissions(roleIds);
            List<PermissionVO> permissionVOS = new ArrayList<>(16);
            for (Permission permission : permissions){
                if (permission.getType().equals(1)){
                    PermissionVO permissionVO = permission2PermissionVO(permission);
                    permissionVOS.add(permissionVO);
                }

            }
            PermissionVO rootPermission = new PermissionVO();
            rootPermission.setId("0");
            rootPermission.setTitle("根节点");
            rootPermission.setParentId("-1");
            List<PermissionVO> rootList = new ArrayList<>(16);
            rootList.add(rootPermission);
            List<PermissionVO> result =  getPermissionVOTree(rootList,permissionVOS);
            return result.get(0).getChildren();
        }
        return null;
    }

    @Override
    public List<PermissionVO> buttons(List<String> roleIds) {
        if (roleIds.size() > 0) {
            List<Permission> permissions = findAllPermissions(roleIds);
            List<PermissionVO> permissionVOS = new ArrayList<>(16);
            for (Permission permission : permissions){
              /*  if (!permission.getLevel().equals(0)){*/
                    PermissionVO permissionVO = permission2PermissionVO(permission);
                    permissionVOS.add(permissionVO);
            /*    }*/
            }
            PermissionVO rootPermission = new PermissionVO();
            rootPermission.setId("0");
            rootPermission.setTitle("根节点");
            rootPermission.setParentId("-1");
            List<PermissionVO> rootList = new ArrayList<>(16);
            rootList.add(rootPermission);
            List<PermissionVO> result =  getPermissionVOTree(rootList,permissionVOS);
            return result.get(0).getChildren();
        }

        return null;
    }

    private List<Permission> findAllPermissions(List<String> roleIds){
        List<Permission> permissions = new ArrayList<>(16);
        if (roleIds.size() > 0){
            List<RolePermission> rolePermissions = rolePermissionDao.findByRoleIdIn(roleIds);
            for (RolePermission rolePermission : rolePermissions){
                Permission permission = permissionDao.getOne(rolePermission.getPermissionId());
                permissions.add(permission);
            }
        }
        return permissions;
    }

    private PermissionVO permission2PermissionVO(Permission permission){
        PermissionVO permissionVO = new PermissionVO();
        permissionVO.setId(permission.getId());
        permissionVO.setParentId(permission.getParentId());
        permissionVO.setTitle(permission.getTitle());
        permissionVO.setPath(permission.getPath());
        permissionVO.setCode(permission.getCode());
        permissionVO.setName(permission.getName());
        permissionVO.setButtonType(permission.getButtonType());
        permissionVO.setIsOpen(permission.getIsOpen());
        permissionVO.setType(permission.getType());
        permissionVO.setSource(permission.getSource());
        return permissionVO;
    }

    private List<PermissionVO> getPermissionVOTree(List<PermissionVO> rootList,List<PermissionVO> permissionVOS){
        TreeToolUtils treeToolUtils = new TreeToolUtils(rootList,permissionVOS);
        List<PermissionVO> result =  treeToolUtils.getTree();
        return result;
    }
}
