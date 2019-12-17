package com.security.plateform.common.util;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.security.plateform.common.constant.CommonConstant;
import com.security.plateform.common.constant.SecurityConstant;
import com.security.plateform.modules.system.entity.Permission;
import com.security.plateform.modules.system.entity.Role;
import com.security.plateform.modules.system.entity.User;
import com.security.plateform.modules.system.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/6 10:26
 * @description  Security安全工具类
 */
@Component
public class SecurityUtil {


    @Autowired
    private UserService userService;


    /**
     * 生成token
     * @param username 账号
     * @param saveLogin 是否保存账号
     * @return
     */
    public String getToken(String username,Boolean saveLogin){
        //生成token
        User u = userService.findByUsername(username);
        List<String> list = new ArrayList<>();
        // 缓存权限
        for(Permission p : u.getPermissions()){
            if(CommonConstant.PERMISSION_OPERATION.equals(p.getType())
                    && StrUtil.isNotBlank(p.getCode())
                    && StrUtil.isNotBlank(p.getPath())) {
                list.add(p.getCode());
            }
        }
        for(Role r : u.getRoles()){
            list.add(r.getRoleName());
        }
        //登录成功生成token
        String token;
            // jwt
            token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
                    //主题 放入用户名
                    .setSubject(u.getUsername())
                    //自定义属性 放入用户拥有请求权限
                    .claim(SecurityConstant.AUTHORITIES, new Gson().toJson(list))
                    //失效时间
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                    .compact();
        return token;
    }


    /**
     * 获取当前登录用户
     * @return
     */
    public User getCurrUser(){

        //从springSecurity容器中 获取当前的用户主题信息
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }


}
