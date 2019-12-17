package com.security.plateform.common.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/11/17 10:49
 * @description 添加审计日志
 */
@Configuration
@Slf4j
public class UserAuditor implements AuditorAware<String>
{
    @Override
    public Optional<String> getCurrentAuditor() {
        UserDetails user;
        try {
            user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.ofNullable(user.getUsername());
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
