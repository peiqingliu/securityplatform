package com.security.platform.config.security.jwt;

import cn.hutool.core.util.StrUtil;
import com.security.platform.common.annotation.SystemLog;
import com.security.platform.common.constant.SecurityConstant;
import com.security.platform.common.enums.LogType;
import com.security.platform.common.utils.ResponseUtil;
import com.security.platform.common.vo.TokenUser;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/10 17:57
 * @description 验证成功处理类  实现默认的springSecurity验证成功处理类
 *
 * 实际认证发生在UsernamePasswordAuthenticationFilter的attemptAuthentication中，
 * 如果认证失败，则会调用unsuccessfulAuthentication进行失败后的处理，一般是提示用户认证失败，要求重新输入用户名和密码，
 * 如果认证成功，那么会调用successfulAuthentication进行成功后的处理，一般是将Authentication存进SecurityContext中并跳转至之前访问的页面或者默认页面
 *
 * UsernamePasswordAuthenticationFilter将拦截请求进行认证. userdetails和Authentication进行对比,认证成功后，
 * 在UsernamePasswordAuthenticationFilter类内有认证成功后的处理方法，此处进行重写
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${us.token.redis}")
    private Boolean tokenRedis;

    @Value("${us.tokenExpireTime}")
    private Integer tokenExpireTime;

    @Value("${us.saveLoginTime}")
    private Integer saveLoginTime;

    @Value("${us.token.storePerms}")
    private Boolean storePerms;


    @Autowired
    private StringRedisTemplate redisTemplate;



    @Override
    @SystemLog(description = "登录系统", type = LogType.LOGIN)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //用户选择保存登录状态几天
        String saveLogin = request.getParameter(SecurityConstant.SAVE_LOGIN);
        Boolean saved = false;
        if(StrUtil.isNotBlank(saveLogin) && Boolean.valueOf(saveLogin)){
            saved = true;
            if(!tokenRedis){
                tokenExpireTime = saveLoginTime * 60 * 24;
            }
        }
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();
        List<String> list = new ArrayList<>();
        for(GrantedAuthority g : authorities){
            list.add(g.getAuthority());
        }
        // 登陆成功生成token
        String token;
        if(tokenRedis){
            // redis
            token = UUID.randomUUID().toString().replace("-", "");
            TokenUser user = new TokenUser(username, list, saved);
            // 不缓存权限
            if(!storePerms){
                user.setPermissions(null);
            }
            // 单点登录 之前的token失效
            String oldToken = redisTemplate.opsForValue().get(SecurityConstant.USER_TOKEN + username);
            if(StrUtil.isNotBlank(oldToken)){
                redisTemplate.delete(SecurityConstant.TOKEN_PRE + oldToken);
            }
            if(saved){
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, saveLoginTime, TimeUnit.DAYS);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), saveLoginTime, TimeUnit.DAYS);
            }else{
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, tokenExpireTime, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), tokenExpireTime, TimeUnit.MINUTES);
            }
        }else{
            // 不缓存权限
            if(!storePerms){
                list = null;
            }
            // jwt
            token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
                    //主题 放入用户名
                    .setSubject(username)
                    //自定义属性 放入用户拥有请求权限
                    .claim(SecurityConstant.AUTHORITIES, new Gson().toJson(list))
                    //失效时间
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000 * 24))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                    .compact();
        }

        ResponseUtil.out(response, ResponseUtil.resultMap(true,200,"登录成功", token));
    }
}
