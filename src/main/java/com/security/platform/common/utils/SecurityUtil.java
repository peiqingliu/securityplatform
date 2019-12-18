package com.security.platform.common.utils;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.security.platform.common.constant.CommonConstant;
import com.security.platform.common.constant.SecurityConstant;
import com.security.platform.common.vo.TokenUser;
import com.security.platform.modules.system.entity.Permission;
import com.security.platform.modules.system.entity.Role;
import com.security.platform.modules.system.entity.User;
import com.security.platform.modules.system.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/6 10:26
 * @description  Security安全工具类
 */
@Component
public class SecurityUtil {

    /**
     * 是否使用redis保存
     */
    @Value("${cetc.token.redis}")
    private Boolean tokenRedis;

    /**
     *
     */
    @Value("${cetc.saveLoginTime}")
    private Integer saveLoginTime;

    /**
     * token过期时间
     */
    @Value("${cetc.tokenExpireTime}")
    private Integer tokenExpireTime;

    /**
     * 是否保存权限
     */
    @Value("${cetc.token.storePerms}")
    private Boolean storePerms;

    @Autowired
    private UserService userService;


    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 生成token
     * @param username 账号
     * @param saveLogin 是否保存账号
     * @return
     */
    public String getToken(String username,Boolean saveLogin){
        Boolean saved = false;
        if (saveLogin != null && saveLogin){
            saved = true;
            if (!tokenRedis){
                tokenExpireTime = saveLoginTime * 60 * 24;
            }
        }
        //生成token
        User u = userService.findByUsername(username);
        List<String> list = new ArrayList<>();
        // 缓存权限
        if(storePerms){
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
        }
        //登录成功生成token
        String token;
        if(tokenRedis){
            // redis
            token = UUID.randomUUID().toString().replace("-", "");
            TokenUser user = new TokenUser(u.getUsername(), list, saved);
            // 单点登录 之前的token失效
            String oldToken = redisTemplate.opsForValue().get(SecurityConstant.USER_TOKEN + username);

            if(StrUtil.isNotBlank(oldToken)){
                redisTemplate.delete(SecurityConstant.TOKEN_PRE + oldToken);
            }
            if(saved){
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + u.getUsername(), token, saveLoginTime, TimeUnit.DAYS);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), saveLoginTime, TimeUnit.DAYS);
            }else{
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + u.getUsername(), token, tokenExpireTime, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), tokenExpireTime, TimeUnit.MINUTES);
            }
        }else{
            // jwt
            token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
                    //主题 放入用户名
                    .setSubject(u.getUsername())
                    //自定义属性 放入用户拥有请求权限
                    .claim(SecurityConstant.AUTHORITIES, new Gson().toJson(list))
                    //失效时间
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                    .compact();
        }
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


    /**
     *  获取当前用户，能够处理的流程类型 TODO
     * @return
     */
    public List<String> getFlowtypeIds(){
        return null;
    }

    /**
     * 通过用户名获取用户拥有权限
     * @param username
     */
    public List<GrantedAuthority> getCurrUserPerms(String username){

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Permission p : userService.findByUsername(username).getPermissions()){
            authorities.add(new SimpleGrantedAuthority(p.getTitle()));
        }
        return authorities;
    }

    /**
     * 获取当前用户的角色ids集合
     * @return
     */
    public List<String> getRoleIds(){
        List<String> roleIds = new ArrayList<>();
        User u = getCurrUser();
        List<Role> roles = u.getRoles();
        roles.forEach(r -> {
            roleIds.add(r.getId());
        });
        return roleIds;
    }




}
